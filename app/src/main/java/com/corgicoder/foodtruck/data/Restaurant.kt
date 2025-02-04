package com.corgicoder.foodtruck.data

data class Restaurant (
    val id: String,
    val name: String,
    val rating: Float,
    val showRating: Boolean,
    val filterIds: List<String>,
    val imageResId: Int, //dummy data, remove when api connected
    // val imageUrl: String,
    val deliveryTimeMinutes: Byte
)

data class RestaurantOpenTime(
    val restaurantId: String,
    val isCurrentlyOpen: Boolean
)