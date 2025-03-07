package com.corgicoder.foodtruck.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.ui.components.card.Card
import com.corgicoder.foodtruck.ui.components.filter.FilterBar
import com.corgicoder.foodtruck.ui.components.header.Header

@Composable
fun HomeScreen(
    onRestaurantClick: (RestaurantData) -> Unit,
    viewModel: HomeViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.loadRestaurants()
    }


    val restaurants by viewModel.restaurantData.observeAsState(emptyList())
    val restaurantsWithFilters by viewModel.restaurantsWithFilterNames.observeAsState(emptyList())

    val isLoading by viewModel.isLoading.observeAsState(false)
  //  val error by viewModel.error.observeAsState()

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (!isLoading && restaurants.isNotEmpty()){
            Column() {
                Spacer(modifier = Modifier.padding(top = 34.dp))
                Header(modifier = Modifier
                    .padding(vertical = 16.dp)
                    .zIndex(1f)
                )
                Box(modifier = Modifier.zIndex(0f)) {
                Row {
                   // FilterBar(filters = filters) {}
                }
                LazyColumn {
                    items(restaurantsWithFilters) { restaurantWithFilters ->
                        Card(
                            restaurant = restaurantWithFilters.restaurant,
                            filters = restaurantWithFilters.filterNames,
                            showRating = true,
                            onRestaurantClick = { onRestaurantClick(restaurantWithFilters.restaurant) }
                        )
                    }
                }
                    }
            }
        }
    }
}