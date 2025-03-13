package com.corgicoder.foodtruck.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corgicoder.foodtruck.data.repository.RestaurantRepository

/*  Enables dependency injection for ViewModels aka... this allows me access to the Repository */

class DetailsViewModelFactory (
    private val repository: RestaurantRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class <T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}