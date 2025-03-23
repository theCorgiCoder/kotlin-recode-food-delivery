package com.corgicoder.foodtruck.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterData(
    val id: String,
    val name: String,
    @Json(name = "image_url")
    val imageUrl: String,
)
