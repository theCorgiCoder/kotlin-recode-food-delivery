package com.corgicoder.foodtruck.feature.details

import androidx.lifecycle.ViewModel
import com.corgicoder.foodtruck.data.DummyData
import com.corgicoder.foodtruck.data.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailsViewModel : ViewModel() {
    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant: StateFlow<Restaurant?> = _restaurant

    fun loadRestaurant(id: String) {
        // In a real app, you'd load this from a repository or API
        // For now, we're using dummy data
        _restaurant.value = DummyData.getDummyRestaurants().find { it.id == id }
    }
}