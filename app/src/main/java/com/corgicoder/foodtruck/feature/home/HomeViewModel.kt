package com.corgicoder.foodtruck.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.model.RestaurantWithFilterNames
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import kotlin.math.log

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

    // Add this for storing the selected restaurant
    private val _selectedRestaurant = MutableStateFlow<RestaurantData?>(null)
    val selectedRestaurant: StateFlow<RestaurantData?> = _selectedRestaurant.asStateFlow()

    private val _filters = MutableStateFlow<List<FilterData>>(emptyList())
    val filters: StateFlow<List<FilterData>> = _filters.asStateFlow()

    private val _filterMap = MutableStateFlow<Map<String, String>>(emptyMap())

    private val _selectedFilterIds = MutableStateFlow<List<String?>>(emptyList())
    val selectedFilterIds: StateFlow<List<String?>> = _selectedFilterIds.asStateFlow()

    private val _openStatus = MutableStateFlow<RestaurantOpenStatus?>(null)
    val openStatus: StateFlow<RestaurantOpenStatus?> = _openStatus.asStateFlow()

    //Combined Data
    private val _restaurantsWithFilterNames = MutableStateFlow<List<RestaurantWithFilterNames>>(emptyList())
    val restaurantsWithFilterNames: StateFlow<List<RestaurantWithFilterNames>> = _restaurantsWithFilterNames.asStateFlow()


    init {
        // Set up automatic filtering when selectedFilterId changes
        viewModelScope.launch {
            _selectedFilterIds
                .collect {
                    if (_allRestaurants.value.isNotEmpty()) {
                        applyCurrentFilter()
                    }
                }
        }
    }

    fun loadRestaurants() {
        if (_isLoading.value) {
            return
        }

        viewModelScope.launch {
            Log.d("HomeViewModel", "Loading restaurants started")
            _isLoading.value = true
            _error.value = null

            try {
                val restaurants = restaurantRepository.fetchRestaurants() ?: emptyList()
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
        println(restaurants)
        val filteredList = if (selectedFilterIds.value.isEmpty()) {
            //when no filter is selected, show ALL restaurants
            restaurants
        } else {
            restaurants.filter { it.filterIds.any{restaurant -> restaurant in selectedFilterIds.value} }
        }
        println("FILTERED LIST: $filteredList")

        // Update the enhanced list with filter names
        val namedFilterList = filteredList.map { restaurant ->
            val filterNames = restaurant.filterIds.mapNotNull { id ->
                filterMap[id]
            }
            println(filterNames)
            RestaurantWithFilterNames(restaurant, filterNames)
        }
        _restaurantsWithFilterNames.value = namedFilterList
    }

    fun filterByRestaurantFilterIds(filterId: String) {
        val filterExists = selectedFilterIds.value.contains(filterId)
        val listOfFilters = selectedFilterIds.value.toMutableList()

        if (filterExists) {
        listOfFilters.remove( filterId )
        } else {
           listOfFilters.add(filterId)
        }
        println(listOfFilters)
        _selectedFilterIds.value = listOfFilters
        // No need to call applyCurrentFilter() here as it's triggered by the Flow collection
    }


    // Helper methods
    fun getFilterNamesForRestaurant(restaurant: RestaurantData): List<String> {
        val filterMap = _filterMap.value
        return restaurant.filterIds.mapNotNull { filterId ->
            filterMap[filterId]
        }
    }

    fun setSelectedRestaurant(restaurant: RestaurantData) {
        _selectedRestaurant.value = restaurant
    }
}