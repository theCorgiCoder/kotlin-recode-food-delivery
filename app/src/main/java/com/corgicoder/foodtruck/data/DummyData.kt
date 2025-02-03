package com.corgicoder.foodtruck.data

object DummyData {
    fun getDummyRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                id = "",
                name = "Bobs Burgers",
                filterIds = listOf("Delivery", "Dine-In", "Take Out"),
                rating = 4.9f,
                showRating = true,
                deliveryTimeMinutes = 30,
                imageResId = ""
            ),
            Restaurant(
                id = "",
                name = "Quarks",
                filterIds = listOf("Dine-In", "Take Out"),
                rating = 1.9f,
                showRating = true,
                deliveryTimeMinutes = 15,
                imageResId = ""
            ),
            Restaurant(
                id = "",
                name = "The Prancing Pony",
                filterIds = listOf("Dine-In"),
                rating = 2.5f,
                showRating = true,
                deliveryTimeMinutes = 54,
                imageResId = ""
            )
        )
    }
}

