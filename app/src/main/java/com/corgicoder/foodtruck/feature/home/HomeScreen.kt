package com.corgicoder.foodtruck.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
    Log.d("HomeScreen", "Composing HomeScreen")
    LaunchedEffect(Unit) {
            viewModel.loadRestaurants()
    }

    val filters = viewModel.filters.collectAsState()
    Log.d("HomeScreen", "Filters Collect: ${filters.value}")
    val selectedFilterId = viewModel.selectedFilterId.collectAsState()
    Log.d("HomeScreen", "Selected filter ID: $selectedFilterId")
    val restaurantsWithFilters = viewModel.restaurantsWithFilterNames.collectAsState()
    Log.d("HomeScreen", "Restaurants count: ${restaurantsWithFilters.value}")

    val isLoading = viewModel.isLoading.collectAsState()
    Log.d("HomeScreen", "Is loading: $isLoading")
  //  val error = viewModel.error.collectAsState()
    val errorState = viewModel.error.collectAsState(initial = null)
    val errorValue = errorState.value
    Log.d("HomeScreen", "Error collected: $errorValue")

    Box(modifier = Modifier.fillMaxSize()
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
                Log.d("HomeScreen", "Showing main content")
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.padding(top = 40.dp))
                Header(modifier = Modifier
                    .padding(vertical = 16.dp)
                    .zIndex(1f)
                )
                Box(modifier = Modifier.zIndex(0f)) {
                  Row {
                        FilterBar(
                            filters = filters.value,
                            selectedFilterId = selectedFilterId.value,
                            onFilterToggled = { filterId ->
                                    viewModel.filterByRestaurantFilterId(filterId)
                            },
                            viewModel = viewModel
                       )
                    }
                    Log.d("HomeScreen", "Showing restaurant list")
                    LazyColumn (modifier = Modifier
                        .padding(vertical = 40.dp)
                    ){
                        items(restaurantsWithFilters.value) { restaurantWithFilters ->
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
}


