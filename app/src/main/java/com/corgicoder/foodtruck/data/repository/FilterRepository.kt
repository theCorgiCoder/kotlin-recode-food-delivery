package com.corgicoder.foodtruck.data.repository

import android.util.Log
import com.corgicoder.foodtruck.data.api.RetrofitClient
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.data.utils.Result

interface FilterRepository {
    suspend fun getFilterById(filterId: String): Result<FilterData>
    suspend fun getAllFilters(): Result<List<FilterData>>
}

class FilterRepositoryImpl() : FilterRepository {
    private val apiService = RetrofitClient.restaurantAPIService

    override suspend fun getFilterById(filterId: String): Result<FilterData> {
        return try {
            val response = apiService.getFilterId(filterId)
            if(response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Empty filter response body"))
            } else {
                Log.e("FilterRepository", "Error: ${response.errorBody()?.string()}")
                Result.Error(Exception ("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("FilterRepository", "Exception: ${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getAllFilters(): Result<List<FilterData>> {
        return try {
            // Fetch restaurants to extract filter IDs
            val restaurantResponse = apiService.getRestaurants()

            if(!restaurantResponse.isSuccessful) {
                Log.e("FilterRepository", "Failed to fetch restaurants: ${restaurantResponse.code()}")
                return Result.Error(Exception("Failes to fetch restaurants: ${restaurantResponse.code()}"))
            }

            val restaurants = restaurantResponse.body()?.restaurants ?: emptyList()
            // Extract unique filter IDs from all restaurants
            val filterIds = restaurants.flatMap { it.filterIds }.distinct()

            if (filterIds.isEmpty()) {
                return Result.Success(emptyList())
            }

            // Fetch each filter individually and collect results
            val filters = mutableListOf<FilterData>()
            val failedFilters = mutableListOf<String>()

            for (filterId in filterIds) {
                when (val result = getFilterById(filterId)) {
                    is Result.Success -> filters.add(result.data)
                    is Result.Error -> {
                        Log.e("FilterRespository", "Failed to fetch filter $filterId: ${result.exception.message}")
                        failedFilters.add(filterId)
                    }
                }
            }

            //If we couldn't fetch any filters, return an error
            if (filters.isEmpty() && filterIds.isNotEmpty()) {
                return Result.Error(Exception("Failed to fetch any filters"))
            }

            // If we fetched some but not all
            if (failedFilters.isNotEmpty()) {
                Log.e("FilterRepository", "Failed to fetch some filters: $failedFilters")
            }

            Result.Success(filters)
        } catch (e: Exception) {
            Log.e("FilterRepository", "Error fetching all filters", e)
            Result.Error(e)
        }
    }

}



