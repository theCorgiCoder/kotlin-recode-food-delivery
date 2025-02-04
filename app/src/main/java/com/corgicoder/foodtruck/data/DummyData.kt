package com.corgicoder.foodtruck.data

import com.corgicoder.foodtruck.R

object DummyData {
    fun getDummyRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                id = "1",
                name = "Bobs Burgers",
                filterIds = listOf("Delivery", "Dine-In", "Take Out"),
                rating = 4.9f,
                showRating = true,
                deliveryTimeMinutes = 30,
                imageResId = R.drawable.alfieprofile
            ),
            Restaurant(
                id = "2",
                name = "Quarks",
                filterIds = listOf("Dine-In", "Take Out"),
                rating = 1.9f,
                showRating = true,
                deliveryTimeMinutes = 15,
                imageResId = R.drawable.alfieprofile
            ),
            Restaurant(
                id = "3",
                name = "The Prancing Pony",
                filterIds = listOf("Dine-In"),
                rating = 2.5f,
                showRating = true,
                deliveryTimeMinutes = 54,
                imageResId = R.drawable.alfieprofile
            )
        )
    }
}

