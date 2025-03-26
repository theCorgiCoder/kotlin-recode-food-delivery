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
            when (val result = filterRepository.getAllFilters()) {
                is Result.Success -> {
                    val filtersList = result.data
                    // Mapping for display: filter ID to filter name
                    val mappingIds = filtersList.associate { filter -> filter.id to filter.name }

                    _filterState.update { it.copy(
                        filters = filtersList,
                        namedFilterIdsMap = mappingIds
                    ) }
                    Log.d("HomeViewModel", "Loaded ${filtersList.size} filters")

                  //  applyCurrentFilter()

                    _uiState.update { it.copy(isLoading = false) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = "Error loading filters: ${result.exception.message}"
                    ) }
                    Log.e("HomeViewModel", "Error loading filters", result.exception)
                }
            }



        }
    }

    private fun applyCurrentFilter() {
        val restaurants = _restaurantState.value.allRestaurants
        val filterMap = _filterState.value.namedFilterIdsMap
        val selectedFilterIds = _filterState.value.selectedFilterIds
        println(restaurants)
        val filteredList = if (selectedFilterIds.isEmpty()) {
            //when no filter is selected, show ALL restaurants
            restaurants
        } else {
            restaurants.filter { restaurant ->
                restaurant.filterIds.any { filterId -> filterId in selectedFilterIds}
             }
        }
        println("FILTERED LIST: $filteredList")

        // Update the enhanced list with filter names
        val namedFilters = filteredList.map { restaurant ->
            val filterNames = restaurant.filterIds.mapNotNull { id ->
                filterMap[id]
            }
            println(filterNames)
            RestaurantWithFilterNames(restaurant, filterNames)
        }
       _restaurantState.update { it.copy(
           restaurantsWithFilterNames = namedFilters
       ) }
    }

    fun filterByRestaurantFilterIds(filterId: String) {
        val currentSelectedFilter = _filterState.value.selectedFilterIds
        val updatedFilters = currentSelectedFilter.toMutableList()

        if (filterId in currentSelectedFilter) {
       updatedFilters.remove(filterId)
        } else {
           updatedFilters.add(filterId)
        }

        _filterState.update { it.copy(
            selectedFilterIds = updatedFilters
        )

        }
        }

    // Helper methods

    fun selectedRestaurant(restaurant: RestaurantData) {
        _detailedState.update { it.copy(
            selectedRestaurant = restaurant
        ) }
    }

    }




