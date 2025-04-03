package com.corgicoder.foodtruck.feature.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.repository.FilterRepository
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.data.utils.FilterUtils
import com.corgicoder.foodtruck.data.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val repository: RestaurantRepository,
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    //Detail related state flows
    private val _detailedState = MutableStateFlow(DetailsState())
    val detailedState: StateFlow<DetailsState> = _detailedState.asStateFlow()

    private val _filterState = MutableStateFlow(DetailsFilterState())
    val filterState: StateFlow<DetailsFilterState> = _filterState.asStateFlow()


    fun fetchRestaurantById(restaurantId: String) {

        if (_uiState.value.isLoading) {
            return
        }

        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = repository.getRestaurantStatus(restaurantId)){

                is Result.Success -> {
                    val status = result.data


                    _detailedState.update { it.copy(
                        openStatus = status
                    ) }

                    _uiState.update { it.copy(isLoading = false, error = null) }
                }

                is Result.Error -> {
                    val exception = result.exception

                    _uiState.update { it.copy(
                        isLoading = false,
                        error = "Error: ${exception.message}"
                    )
                    }
                 }
            }

            when (val result = repository.getRestaurantById(restaurantId)){

                is Result.Success -> {
                    val restaurantData = result.data

                    _detailedState.update { it.copy(
                        selectedRestaurant = restaurantData,
                    ) }

                    if (restaurantData.filterIds.isNotEmpty()) {
                        loadRestaurantFilters()
                    } else {
                        _uiState.update { it.copy(
                            isLoading = false,
                            error = "No Data Available"
                        ) }
                    }

                    _uiState.update { it.copy(isLoading = false, error = null) }

                }

                is Result.Error -> {
                    val exception = result.exception

                    _uiState.update { it.copy(
                        isLoading = false,
                        error = "Error: ${exception.message}"
                    )
                    }
                }
            }
        }
    }

  private fun loadRestaurantFilters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = filterRepository.getAllFilters()) {
                is Result.Success -> {
                    val filtersList = result.data
                    // Create mapping from filter ID to filter name
                    val mappingIds = filtersList.associate { filter -> filter.id to filter.name }
                    println("Filter map size: ${mappingIds.size}")
                    _filterState.update { it.copy(
                        filters = filtersList,
                        namedFilterIdsMap = mappingIds
                    ) }

                    // If we already have restaurant data, update the filter names
                    _detailedState.value.selectedRestaurant?.let { restaurant ->
                        val filterNames = FilterUtils.getFilterNamesForRestaurant(restaurant, mappingIds)

                       _detailedState.update { it.copy(namedFilters = filterNames) }
                        println("Filter NAMES: ${detailedState.value.namedFilters}")

                    }

                    println("Filter map: $mappingIds")
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Result.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = "Error loading filters: ${result.exception.message}"
                    ) }
                    Log.e("DetailsViewModel", "Error loading filters", result.exception)
                }
            }
        }
    }


}
