package com.corgicoder.foodtruck.data.model

import com.squareup.moshi.Json


data class RestaurantList(
    val restaurants: List<Restaurant>
)

data class Restaurant (
    val rating: Float,
    val id: String,
    val filterIds: List<String>,
    val name: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "delivery_time_minutes") val deliveryTimeMinutes: Int?,
)

data class RestaurantOpenTime(
    val restaurantId: String,
    val isCurrentlyOpen: Boolean
)

data class Filter(
    val id: String,
    val name: String,
    @Json(name = "image_url") val imageUrl: String,
)