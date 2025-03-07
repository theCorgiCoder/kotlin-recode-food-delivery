package com.corgicoder.foodtruck.feature.home
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantWithFilterNames
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //Calling RestaurantRepo to fetch data
    private val restaurantRepository: RestaurantRepository = RestaurantRepository()

    //Restaurant Data
    private val _restaurantData = MutableLiveData<List<RestaurantData>>()
    val restaurantData: LiveData<List<RestaurantData>> = _restaurantData

    //Filter Data
    private val _filters = MutableLiveData<List<FilterData>>()
    val filters: LiveData<List<FilterData>> = _filters

    //Filtered Restaurant Data
    private val _filteredRestaurants = MutableLiveData<List<RestaurantData>>()
    val filteredRestaurants: LiveData<List<RestaurantData>> = _filteredRestaurants

    // Map to store filter ID to filter name mapping
    private val _filterMap = MutableLiveData<Map<String, String>>()
    val filterMap: LiveData<Map<String, String>> = _filterMap

    // Restaurant with filter names
    private val _restaurantsWithFilterNames = MutableLiveData<List<RestaurantWithFilterNames>>()
    val restaurantsWithFilterNames: LiveData<List<RestaurantWithFilterNames>> = _restaurantsWithFilterNames

    //Loading Status
    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
            loadRestaurants()
    }

    fun loadRestaurants() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            try {
                val restaurants = restaurantRepository.fetchRestaurants()
                val restaurantList = restaurants ?: emptyList()

                _restaurantData.postValue(restaurantList)

                if (restaurantList.isNotEmpty()) {
                    // After loading restaurants, extract unique filter IDs
                    loadFilters(restaurantList)
                } else {
                    _error.postValue("Failed to fetch restaurant data.")
            }
        } catch (e: Exception) {
            _error.postValue("An error occurred: ${e.message}")
                Log.e("HomeViewModel", "Error loading restaurants", e)
                //set empty list on error
                _restaurantData.postValue(emptyList())

            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Fetch list of Filter information
    private fun loadFilters(restaurants: List<RestaurantData>) {
        viewModelScope.launch {
            try {
                //Extract unique IDs from all restaurants
                val filterIds = restaurants
                    .flatMap { it.filterIds }
                    .distinct()

                // Fetch filter data for each ID
                val filtersList = mutableListOf<FilterData>()
                val filterIdToNameMap = mutableMapOf<String, String>()

                for (filterId in filterIds) {
                    val filterData = restaurantRepository.fetchFilterId(filterId)
                    if(filterData != null) {
                        filtersList.add(filterData)
                        // Store the mapping of ID to name
                        filterIdToNameMap[filterData.id] = filterData.name
                    }
                }

                _filters.postValue(filtersList)
                _filterMap.postValue(filterIdToNameMap)

                // Now create the enhanced restaurant list with filter names
                createRestaurantsWithFilterNames(restaurants, filterIdToNameMap)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading filters", e)
            }
        }
    }

    // Create a list of restaurants with their filter names
    private fun createRestaurantsWithFilterNames(
        restaurants: List<RestaurantData>,
        filterMap: Map<String, String>
    ) {
        val enhancedList = restaurants.map { restaurant ->
            val filterNames = restaurant.filterIds.mapNotNull { filterId ->
                filterMap[filterId]
            }
            RestaurantWithFilterNames(restaurant, filterNames)
        }
        _restaurantsWithFilterNames.postValue(enhancedList)
    }

    // Filter restaurants based on selected filter
    fun filterByRestaurantFilterId(filterId: String) {
        val restaurants = _restaurantData.value ?: return

        if (filterId.isEmpty()) {
            // If no filter selected, show all restaurants
            _filteredRestaurants.postValue(restaurants)
        } else {
            // Filter restaurants that have the selected filter ID
            val filtered = restaurants.filter { it.filterIds.contains(filterId) }
            _filteredRestaurants.postValue(filtered)
        }

        // Also update the enhanced list
        val filterMap = _filterMap.value ?: emptyMap()
        createRestaurantsWithFilterNames(
            _filteredRestaurants.value ?: emptyList(),
            filterMap
        )
    }

    // Get filter name by ID
    fun getFilterName(filterId: String): String {
        return _filterMap.value?.get(filterId) ?: "Unknown"
    }

    // Get all filter names for a restaurant
    fun getFilterNamesForRestaurant(restaurant: RestaurantData): List<String> {
        val filterMap = _filterMap.value ?: return emptyList()
        return restaurant.filterIds.mapNotNull { filterId ->
            filterMap[filterId]
        }
    }
}


