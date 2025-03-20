package com.corgicoder.foodtruck.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val filters = viewModel.filters.collectAsState()
    val selectedFilterIds = viewModel.selectedFilterIds.collectAsState()
    val restaurantsWithFilters = viewModel.restaurantsWithFilterNames.collectAsState()

    val isLoading = viewModel.isLoading.collectAsState()

    val errorState = viewModel.error.collectAsState(initial = null)
    val errorValue = errorState.value

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        when { isLoading.value -> {
             Log.d("HomeScreen", "Showing loading indicator")
          CircularProgressIndicator(
                 modifier = Modifier.align(Alignment.Center)
             )
        }
            errorValue != null -> {
                Log.d("HomeScreen", "Showing error: $errorValue")

            }
            else -> {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.TopCenter)
                        .zIndex(1f),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Column (
                        modifier = Modifier.padding(16.dp)
                    ){
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        Header(
                            modifier = Modifier

                        )
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        FilterBar(
                            filters = filters.value,
                            selectedFilterIds = selectedFilterIds.value,
                            onFilterToggled = { filterId ->
                                println("Filter Toggled")
                                viewModel.filterByRestaurantFilterIds(filterId)
                            },
                            viewModel = viewModel
                        )
                    }
                }

            Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn (
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 200.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ){
                        items(restaurantsWithFilters.value) { restaurantWithFilters ->
                            Card(
                                restaurant = restaurantWithFilters.restaurant,
                                filters = restaurantWithFilters.filterNames,
                                showRating = true,
                                onRestaurantClick = { onRestaurantClick(restaurantWithFilters.restaurant) },
                            )
                        }
                    }
                }
            }
        }
    }
}


