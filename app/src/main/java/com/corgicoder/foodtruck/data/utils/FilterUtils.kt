package com.corgicoder.foodtruck.data.utils

import com.corgicoder.foodtruck.data.model.RestaurantData

object FilterUtils {

    /**
     * Maps a list of filter IDs to their corresponding names using the provided mapping.
     *
     * @param filterIds List of filter IDs to be mapped to names
     * @param filterIdToNameMap Mapping of filter IDs to their display names
     * @return List of filter names corresponding to the provided IDs
     */
   private fun mapFilterIdsToNames(
        filterIds: List<String>,
        filterIdToNameMap: Map<String, String>
    ) : List <String> {
        return filterIds.mapNotNull { filterId ->
            filterIdToNameMap[filterId]
        }
    }

    /**
     * Convenience function to get filter names for a restaurant.
     *
     * @param restaurant The restaurant containing filter IDs
     * @param filterIdToNameMap Mapping of filter IDs to their display names
     * @return List of filter names for the restaurant
     */
    fun getFilterNamesForRestaurant(
        restaurant: RestaurantData,
        filterIdToNameMap: Map<String, String>
    ): List<String> {
        return mapFilterIdsToNames(restaurant.filterIds, filterIdToNameMap)
    }
}

