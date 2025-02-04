package com.corgicoder.foodtruck.feature.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.corgicoder.foodtruck.data.Restaurant
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.ui.components.card.Card

@Composable
fun HomeScreen(

    onRestaurantClick: (Restaurant) -> Unit
) {
    val  viewModel: HomeViewModel = viewModel()
    val restaurants by viewModel.restaurants.collectAsState()

    LazyColumn {
        items(restaurants) { restaurant ->
            Card(
                restaurant = restaurant,
                onRestaurantClick = { onRestaurantClick(restaurant) }
            )
        }
    }
}