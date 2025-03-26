package com.corgicoder.foodtruck.data.repository

import android.util.Log
import com.corgicoder.foodtruck.data.utils.Result
import com.corgicoder.foodtruck.data.api.RetrofitClient
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus

interface RestaurantRepository {
    suspend fun getRestaurants(): Result<List<RestaurantData>>
    suspend fun getRestaurantStatus(restaurantId: String): Result<RestaurantOpenStatus>
}

class RestaurantRepositoryImpl : RestaurantRepository {
    private val apiService = RetrofitClient.restaurantAPIService

    override suspend fun getRestaurants(): Result<List<RestaurantData>> {
        return try {
            val response = apiService.getRestaurants()
            if (response.isSuccessful) {
                Result.Success(response.body()?.restaurants ?: emptyList())
            } else {
                Result.Error(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getRestaurantStatus(restaurantId: String): Result<RestaurantOpenStatus> {
        return try {
            val response = apiService.getOpenStatus(restaurantId)
            if (response.isSuccessful) {
              response.body()?.let {
                  Result.Success(it)
              } ?: Result.Error(Exception("Empty restaurant response body"))
            } else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                Result.Error(Exception ("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception: ${e.message}")
            Result.Error(e)
        }
    }
}