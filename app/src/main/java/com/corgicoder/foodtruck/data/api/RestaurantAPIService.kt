package com.corgicoder.foodtruck.data.api

import com.corgicoder.foodtruck.data.model.Filter
import com.corgicoder.foodtruck.data.model.RestaurantList
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RestaurantAPIService {
    @Headers("accept: application/json")

    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantList

    @GET("filter/{id}")
    suspend fun getFilterById(@Path("id") id: String) : Filter
}