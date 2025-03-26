package com.corgicoder.foodtruck.feature.details

import androidx.lifecycle.ViewModel
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import android.util.Log

import kotlinx.coroutines.launch

class DetailsViewModel (
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _openStatus = MutableStateFlow<RestaurantOpenStatus?>(null)
    val openStatus: StateFlow<RestaurantOpenStatus?> = _openStatus.asStateFlow()

    private val _isLoadingStatus = MutableStateFlow(false)
    val isLoadingStatus: StateFlow<Boolean> = _isLoadingStatus.asStateFlow()

    private val _statusError = MutableStateFlow<String?>(null)
    val statusError: StateFlow<String?> = _statusError.asStateFlow()

    fun fetchOpenStatus(restaurantId: String) {
        viewModelScope.launch {
            _isLoadingStatus.value = true
            _statusError.value = null
/*
            try {
                val status = repository.fetchRestaurantStatus(restaurantId)
                _openStatus.value = status
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching restaurant status", e)
                _statusError.value = "Could not load restaurant status: ${e.message}"
            } finally {
                _isLoadingStatus.value = false
            }
            */
        }
    }
}