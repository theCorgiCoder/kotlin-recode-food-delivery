package com.corgicoder.foodtruck.feature.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.corgicoder.foodtruck.data.Restaurant
import com.corgicoder.foodtruck.ui.components.card.Card

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onRestaurantClick: (Restaurant) -> Unit
) {
    val restaurants by viewModel.restaurants.collectAsState()

    LazyColumn {
        items(restaurants) { restaurant ->
            Card(
                restaurant = restaurant,
                onCardClick = { onRestaurantClick(restaurant) }
            )
        }
    }
}