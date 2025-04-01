package com.corgicoder.foodtruck.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.feature.uiState.RestaurantDetailsState
import com.corgicoder.foodtruck.feature.uiState.UiState
import com.corgicoder.foodtruck.data.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val repository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    //Detail related state flows
    private val _detailedState = MutableStateFlow(RestaurantDetailsState())
    val detailedState: StateFlow<RestaurantDetailsState> = _detailedState.asStateFlow()


    fun fetchOpenStatus(restaurantId: String) {
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
                        selectedRestaurant = restaurantData
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
        }
    }
}
