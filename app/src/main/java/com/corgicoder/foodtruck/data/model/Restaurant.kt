package com.corgicoder.foodtruck.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantsResponse(
    val restaurants: List<RestaurantData>
)

@JsonClass(generateAdapter = true)
data class RestaurantData (
    val rating: Float,
    val id: String,
    val filterIds: List<String>,
    val name: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "delivery_time_minutes")
    val deliveryTimeMinutes: Int?,
)

@JsonClass(generateAdapter = true)
data class RestaurantWithFilterNames (
    val restaurant: RestaurantData,
    val filterNames: List<String>
)