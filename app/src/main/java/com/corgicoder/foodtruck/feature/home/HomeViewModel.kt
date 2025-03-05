package com.corgicoder.foodtruck.feature.home
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.RetrofitClient
import com.corgicoder.foodtruck.data.model.Filter
import com.corgicoder.foodtruck.data.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _restaurantsData = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> get() = _restaurantsData

    private val _filtersData = MutableStateFlow<Map<String, Filter>>(emptyMap())
    val filtersData: StateFlow<Map<String, Filter>> get() = _filtersData

    init {
        viewModelScope.launch (Dispatchers.IO){
            getRestaurants()
            fetchFiltersParallel()
        }
    }

    private suspend fun getRestaurants() {
       try {
           val restaurantList = RetrofitClient.restaurantAPIService.getRestaurants()
           val restaurants = restaurantList.restaurants
           _restaurantsData.value = restaurants


           Log.d("HomeViewModel", "Fetched ${restaurants.size} restaurants")
       } catch (e: Exception) {
           Log.e("HomeViewModel", "Error fetching restaurants", e)
       }
    }

    private suspend fun fetchFilters(): List<Filter> {
        return try {
            val response = RetrofitClient.restaurantAPIService.getFilters()

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("HomeViewModel", "Failed to fetch filters: ${response.errorBody()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel Fetch filterIds", "Error fetching filter IDs", e)
            emptyList()
        }
    }

    private suspend fun fetchFiltersParallel() {
        try {
            //Fetch all filters at once
            val filters = fetchFilters()

            // Convert the list of filters to a map with ID
            val filterMap = filters.associateBy { it.id }

            //Update the state with the fetched filters
            _filtersData.value= filterMap

            Log.d("HomeViewModel", "Fetched ${filters.size} filters")
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error fetching filters", e)
        }
    }
}


