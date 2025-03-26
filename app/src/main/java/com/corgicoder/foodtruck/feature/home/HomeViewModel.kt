package com.corgicoder.foodtruck.feature.home

import android.net.NetworkInfo.DetailedState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.model.RestaurantWithFilterNames
import com.corgicoder.foodtruck.data.repository.FilterRepository
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.feature.uiState.FilterState
import com.corgicoder.foodtruck.feature.uiState.RestaurantDetailsState
import com.corgicoder.foodtruck.feature.uiState.RestaurantListState
import com.corgicoder.foodtruck.data.utils.Result
import com.corgicoder.foodtruck.feature.uiState.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.log

class HomeViewModel (
    private val restaurantRepository: RestaurantRepository,
    private val filterRepository: FilterRepository
) : ViewModel() {

    //Error and Loading Ui state flows
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    //Restaurant related state flows
    private val _restaurantState = MutableStateFlow(RestaurantListState())
    val restaurantState: StateFlow<RestaurantListState> = _restaurantState.asStateFlow()

    // Filter related state flows
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    //Detail related state flows
    private val _detailedState = MutableStateFlow(RestaurantDetailsState())
    val detailedState: StateFlow<RestaurantDetailsState> = _detailedState.asStateFlow()

    /*
    * // Example of updating loading state
_uiState.update { it.copy(isLoading = true, error = null) }

// Example of updating restaurant list
_restaurantState.update { it.copy(allRestaurants = restaurants) }

// Example of updating filters
_filterState.update { it.copy(
    availableFilters = filtersList,
    filterIdToNameMap = filterIdToNameMap
) }

// Example of updating selected restaurant
_detailState.update { it.copy(selectedRestaurant = restaurant) }
    *
    * */

    init {
        // Set up automatic filtering when selectedFilterId changes
        viewModelScope.launch {
            _filterState
                .map { it.selectedFilterIds }
                .collect{
                    if (_restaurantState.value.allRestaurants.isNotEmpty()) {
                        applyCurrentFilter()
                    }
                }
        }
    }

    fun loadRestaurants() {
        if (_uiState.value.isLoading) {
            return
        }

        viewModelScope.launch {
            // Update loading state
            _uiState.update { it.copy(isLoading = true, error = null) }

            Log.d("HomeViewModel", "Loading restaurants started")

          when (val result = restaurantRepository.getRestaurants()) {
              is Result.Success -> {

                  val restaurants = result.data
                  //Update state
                  _restaurantState.update { it.copy(allRestaurants = restaurants) }

                  if (restaurants.isNotEmpty()) {
                     loadFilters(restaurants)
                  } else {
                      _uiState.update { it.copy(
                          isLoading = false,
                          error = "No Data Available"
                      ) }
                  }
              }

              is Result.Error -> {
                val exception = result.exception

                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Error: ${exception.message}"
                ) }

              Log.e("ViewModel", "Error getting data", exception)

              }
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