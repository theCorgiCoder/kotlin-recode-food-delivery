package com.corgicoder.foodtruck.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantOpenStatus(
    @Json(name = "restaurant_id")
    val restaurantId: String,
    @Json(name = "is_currently_open")
    val isCurrentlyOpen: Boolean
)
