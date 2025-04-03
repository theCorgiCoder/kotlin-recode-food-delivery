package com.corgicoder.foodtruck.feature.details

import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus

data class DetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

data class DetailsState(
    val selectedRestaurant: RestaurantData? = null,
    val namedFilters: List<String> = emptyList(),
    val openStatus: RestaurantOpenStatus? = null,
)

data class DetailsFilterState(
    val filters: List<FilterData> = emptyList(),
    val namedFilterIdsMap: Map<String, String> = emptyMap(),
    val selectedFilterIds: List<String> = emptyList()
)