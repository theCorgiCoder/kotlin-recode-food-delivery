package com.corgicoder.foodtruck.feature.home

import androidx.lifecycle.ViewModel
import com.corgicoder.foodtruck.data.DummyData
import com.corgicoder.foodtruck.data.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel {
    class HomeViewModel : ViewModel() {
        private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
        val restaurants: StateFlow<List<Restaurant>> = _restaurants

        init {
            loadRestaurants()
        }
        //change loadRestaurants when implementing real data
        private fun loadRestaurants() {
            _restaurants.value = DummyData.getDummyRestaurants()
        }


    }

}