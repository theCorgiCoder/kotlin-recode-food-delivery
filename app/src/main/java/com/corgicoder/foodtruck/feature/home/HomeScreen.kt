package com.corgicoder.foodtruck.feature.home

import android.util.Log
import androidx.compose.foundation.background
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

    val selectedFilterIds = viewModel.filterState.collectAsState()
    val restaurantsWithFilters = viewModel.restaurantState.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        when { uiState.value.isLoading -> {
          CircularProgressIndicator(
                 modifier = Modifier.align(Alignment.Center)
             )
        }
            uiState.value.error != null -> {
                Log.d("HomeScreen", "Showing error: ${uiState.value.error}")

            }
            else -> {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.TopCenter)
                        .zIndex(1f),
                    color = Color(0xFFF8F8F8),

                ) {
                    Column (
                        modifier = Modifier.padding(16.dp)
                    ){
                        Spacer(modifier = Modifier.padding(vertical = 16.dp))
                        Header(modifier = Modifier)
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        FilterBar(
                            filters = selectedFilterIds.value.filters,
                            selectedFilterIds = selectedFilterIds.value.selectedFilterIds,
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
                        items(restaurantsWithFilters.value.restaurantsWithFilterNames) { restaurantWithFilters ->
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


