package com.corgicoder.foodtruck.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.corgicoder.foodtruck.data.model.Restaurant
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.data.model.Filter
import com.corgicoder.foodtruck.ui.components.card.Card
import com.corgicoder.foodtruck.ui.components.filter.FilterBar

@Composable
fun HomeScreen(
    onRestaurantClick: (Restaurant) -> Unit,
    onFilterClick: (Filter) -> Unit
) {
    val  viewModel: HomeViewModel = viewModel()
    val restaurants by viewModel.restaurants.collectAsState()
    val filters by viewModel.filtersData.collectAsState()

    Column() {
        Row {
  //filterbar here
        }
        LazyColumn {
            items(restaurants) { restaurant ->
                Card(
                    restaurant = restaurant,
                    filters = filters,
                    showRating = true,
                    onRestaurantClick = { onRestaurantClick(restaurant) }
                )
            }
        }
    }
}