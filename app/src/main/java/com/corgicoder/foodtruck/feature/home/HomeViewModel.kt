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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    lateinit var restaurants: List<Restaurant>
    lateinit var filtersData: List<Filter>

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            fetchData()
        }
    }

    private suspend fun fetchData() {
        _isLoading.value = true
        try {
           getRestaurants()
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error fetching data", e)
        }
        finally {
            _isLoading.value = false
        }
    }

    private suspend fun getRestaurants() {
      try {
           val restaurantList = RetrofitClient.restaurantAPIService.getRestaurants()
           restaurants = restaurantList.restaurants
           Log.d("HomeViewModel", "Fetched ${restaurants.size} restaurants")

       } catch (e: Exception) {
           Log.e("HomeViewModel", "Error fetching restaurants", e)

       }
    }
/*
    private suspend fun fetchFilters() {
        try {
            if (::restaurants.isInitialized) {
                val getRestaurantFilters =
                    restaurants.flatMap { it.filterIds }.toSet() //creates list that is all unique
                val filters = coroutineScope {
                    getRestaurantFilters.map { id ->
                        async { RetrofitClient.restaurantAPIService.getFilters(id) }
                    }.awaitAll()
                }
                filtersData = filters
            } else {
                Log.e("HomeViewModel", "Restaurants not initialized")
            }
        } catch (e: Exception) {
                Log.e("HomeViewModel Fetch filterIds", "Error fetching filter IDs", e)
            filtersData = emptyList()
        }
    }
*/
   /* private suspend fun fetchFiltersParallel() {
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
    } */
}


