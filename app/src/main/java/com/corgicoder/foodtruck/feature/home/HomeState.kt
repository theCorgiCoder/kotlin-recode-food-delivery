package com.corgicoder.foodtruck.feature.home

import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.model.RestaurantWithFilterNames

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

data class RestaurantListState(
    val allRestaurants: List<RestaurantData> = emptyList(),
    val restaurantsWithFilterNames: List<RestaurantWithFilterNames> = emptyList()
)

data class FilterState(
    val filters: List<FilterData> = emptyList(),
    val namedFilterIdsMap: Map<String, String> = emptyMap(),
    val selectedFilterIds: List<String> = emptyList()
)

data class RestaurantDetailsState(
    val selectedRestaurant: RestaurantData? = null,
    val openStatus: RestaurantOpenStatus? = null,
)