package com.corgicoder.foodtruck.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantWithFilterNames
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //Calling RestaurantRepo to fetch data
    private val restaurantRepository: RestaurantRepository = RestaurantRepository()

    //UI State
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    //Data
    private val _allRestaurants = MutableStateFlow<List<RestaurantData>>(emptyList())

    private val _filters = MutableStateFlow<List<FilterData>>(emptyList())
    val filters: StateFlow<List<FilterData>> = _filters.asStateFlow()

    private val _filterMap = MutableStateFlow<Map<String, String>>(emptyMap())

    private val _selectedFilterId = MutableStateFlow<String?>(null)
    val selectedFilterId: StateFlow<String?> = _selectedFilterId.asStateFlow()

    //Combined Data
    private val _restaurantsWithFilterNames = MutableStateFlow<List<RestaurantWithFilterNames>>(emptyList())
    val restaurantsWithFilterNames: StateFlow<List<RestaurantWithFilterNames>> = _restaurantsWithFilterNames.asStateFlow()


    init {
        // Set up automatic filtering when selectedFilterId changes
        viewModelScope.launch {
            _selectedFilterId
                .collect {
                    if (_allRestaurants.value.isNotEmpty()) {
                        applyCurrentFilter()
                    }
                }
        }
    }

    fun loadRestaurants() {
        if (_isLoading.value) {
            Log.d("HomeViewModel", "Skipping loadRestaurants - already loading or loaded")
            return
        }

        viewModelScope.launch {
            Log.d("HomeViewModel", "Loading restaurants started")
            _isLoading.value = true
            _error.value = null

            try {
                val restaurants = restaurantRepository.fetchRestaurants() ?: emptyList()
                Log.d("HomeViewModel", "Fetched ${restaurants.size} restaurants")
                _allRestaurants.value = restaurants

                if (restaurants.isNotEmpty()) {
                    loadFilters(restaurants)
                } else {
                    _error.value = "Failed to fetch restaurant data."
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
                Log.e("HomeViewModel", "Error loading restaurants", e)
                _allRestaurants.value = emptyList()
                _isLoading.value = false
            }
        }
    }

    private fun loadFilters(restaurants: List<RestaurantData>) {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Loading filters started")
            try {
                val filterIds = restaurants.flatMap { it.filterIds }.distinct()
                Log.d("HomeViewModel", "Found ${filterIds.size} unique filter IDs")
                val filtersList = mutableListOf<FilterData>()
                val filterIdToNameMap = mutableMapOf<String, String>()

                for (filterId in filterIds) {
                    restaurantRepository.fetchFilterId(filterId)?.let { filterData ->
                        filtersList.add(filterData)
                        filterIdToNameMap[filterData.id] = filterData.name
                        Log.d("HomeViewModel", "Loaded filter: ${filterData.id} = ${filterData.name}")

                    }
                }

                _filters.value = filtersList
                _filterMap.value = filterIdToNameMap

                Log.d("HomeViewModel", "Loaded ${filtersList.size} filters")

                // Apply current filter to the new data
                applyCurrentFilter()

                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading filters", e)
                _error.value = "Error loading filters: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    private fun applyCurrentFilter() {
        val restaurants = _allRestaurants.value
        val filterMap = _filterMap.value
        val filterId = _selectedFilterId.value

        Log.d("HomeViewModel", "Applying filter: $filterId")
        Log.d("HomeViewModel", "Restaurant count: ${restaurants.size}")
        Log.d("HomeViewModel", "Filter map size: ${filterMap.size}")

        if (restaurants.isEmpty()) {
            Log.w("HomeViewModel", "No restaurants to filter")
            _restaurantsWithFilterNames.value = emptyList()
            return
        }

        val filteredList = if (filterId.isNullOrEmpty()) {
            //when no filter is selected, show ALL restaurants
            restaurants
        } else {
            restaurants.filter { it.filterIds.contains(filterId) }
        }
        Log.d("HomeViewModel", "Filtered to ${filteredList.size} restaurants")
        // Update the enhanced list with filter names
        val enhancedList = filteredList.map { restaurant ->
            val filterNames = restaurant.filterIds.mapNotNull { id ->
                filterMap[id]
            }
            RestaurantWithFilterNames(restaurant, filterNames)
        }
        Log.d("HomeViewModel", "Created ${enhancedList.size} enhanced restaurant objects")
        _restaurantsWithFilterNames.value = enhancedList
    }

    fun filterByRestaurantFilterId(filterId: String?) {
        _selectedFilterId.value = filterId
        // No need to call applyCurrentFilter() here as it's triggered by the Flow collection
    }

    fun clearFilter() {
        _selectedFilterId.value = null
        // No need to call applyCurrentFilter() here as it's triggered by the Flow collection
    }

    fun toggleFilter(filterId: String) {
        if(_selectedFilterId.value == filterId) {
            _selectedFilterId.value = null
        }else {
            _selectedFilterId.value = filterId
        }
    }

    // Helper methods
    fun getFilterName(filterId: String): String {
        return _filterMap.value[filterId] ?: "Unknown"
    }
}