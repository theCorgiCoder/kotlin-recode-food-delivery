package com.corgicoder.foodtruck.data.repository

import android.util.Log
import com.corgicoder.foodtruck.data.api.RetrofitClient
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.model.RestaurantsResponse
import retrofit2.Response

class RestaurantRepository {
    private val apiService = RetrofitClient.restaurantAPIService

    // Fetch restaurant list
    suspend fun fetchRestaurants(): List<RestaurantData>? {
        return try {
            val response: Response<RestaurantsResponse> = apiService.getRestaurants()
            if (response.isSuccessful) {
                response.body()?.restaurants
            }else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                emptyList()
            }
        }
        catch (e: Exception) {
            Log.e("RestaurantRepository", "Error: ${e.message}")
            emptyList()
        }
    }

    // Fetch filters by id
    suspend fun fetchFilterId(filterId: String): FilterData? {
        return try {
            val response = apiService.getFilterId(filterId)

            if(response.isSuccessful) {
                response.body()
            }else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception: ${e.message}")
            null
        }
    }

    // Fetch Open Status of restaurant
    suspend fun fetchRestaurantStatus(restaurantId: String): RestaurantOpenStatus? {
        return try {
            val response = apiService.getOpenStatus(restaurantId)

            if(response.isSuccessful) {
                response.body()
            }else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception: ${e.message}")
            null}
    }

}