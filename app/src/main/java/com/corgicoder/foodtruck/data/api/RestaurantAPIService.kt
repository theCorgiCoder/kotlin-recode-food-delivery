package com.corgicoder.foodtruck.data.api

import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.model.RestaurantsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantAPIService {
    @GET("restaurants")
    suspend fun getRestaurants(): Response<RestaurantsResponse>

    @GET("filter/{id}")
    suspend fun getFilterId(@Path("id") filterId: String,): Response<FilterData>

    @GET("open/{id}")
    suspend fun getOpenStatus(@Path("id") restaurantId: String): Response<RestaurantOpenStatus>
}