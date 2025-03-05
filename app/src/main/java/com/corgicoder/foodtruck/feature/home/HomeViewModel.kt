package com.corgicoder.foodtruck.feature.home
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.RetrofitClient
import com.corgicoder.foodtruck.data.model.Filter
import com.corgicoder.foodtruck.data.model.Restaurant
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
        viewModelScope.launch {
            getRestaurants()
        }
    }

    private suspend fun getRestaurants() {
       try {
           val restaurantList = RetrofitClient.restaurantAPIService.getRestaurants()
           val restaurants = restaurantList.restaurants
           _restaurantsData.value = restaurants

           //Extract unique filter IDs
           val uniqueFilterIds = extractUniqueFilterIds(restaurants)
           Log.d("HomeViewModel IDs", "Unique Filter IDs: $uniqueFilterIds")

           //fetch filters for each unique ID
           fetchFiltersParallel(uniqueFilterIds)


           Log.d("HomeViewModel", "Fetched ${restaurants.size} restaurants")
       } catch (e: Exception) {
           Log.e("HomeViewModel", "Error fetching restaurants", e)
       }
    }

    private fun extractUniqueFilterIds(restaurants: List<Restaurant>): List<String> {
        return restaurants
            .flatMap { it.filterIds } // Ensure 'restaurants' is a List<Restaurant>
            .distinct()
    }

    private suspend fun fetchFiltersParallel(filterIds: List<String>) {
        val filterMap = mutableMapOf<String, Filter>()

        coroutineScope {
            val deferredFilters = filterIds.map { id ->
                async {
                    try {
                        val filter = RetrofitClient.restaurantAPIService.getFilterById(id)
                        filterMap[id] = filter
                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "Error fetching filter with ID: $id", e)
                    }
                }
            }
            deferredFilters.awaitAll()
        }

        _filtersData.value = filterMap
    }
}


