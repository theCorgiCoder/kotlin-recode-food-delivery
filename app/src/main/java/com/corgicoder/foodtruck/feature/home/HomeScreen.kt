package com.corgicoder.foodtruck.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.repository.FilterRepositoryImpl
import com.corgicoder.foodtruck.data.repository.RestaurantRepositoryImpl
import com.corgicoder.foodtruck.ui.components.card.RestaurantCard
import com.corgicoder.foodtruck.ui.components.filter.FilterBar
import com.corgicoder.foodtruck.ui.components.header.Header

@Composable
fun HomeScreen(
    onRestaurantClick: (RestaurantData) -> Unit,
) {

    val viewModel: HomeViewModel = viewModel {
        HomeViewModel(
            filterRepository = FilterRepositoryImpl(),
            restaurantRepository = RestaurantRepositoryImpl()
        )
    }

    val selectedFilterIds = viewModel.filterState.collectAsState()
    val restaurantsWithFilters = viewModel.restaurantState.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
            viewModel.loadRestaurants()
        println("HOME LOAD RESTAURANTS")
    }

    if (uiState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )}

    } else {
    Scaffold (
        modifier = Modifier.background(Color.Black),
        content = { paddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                        ){

                            Header(modifier = Modifier)

                            FilterBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                filters = selectedFilterIds.value.filters,
                                selectedFilterIds = selectedFilterIds.value.selectedFilterIds,
                                onFilterToggled = { filterId ->
                                    viewModel.filterByRestaurantFilterIds(filterId)
                                }
                            ) }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        ) {
                           items(restaurantsWithFilters.value.restaurantsWithFilterNames) { restaurantWithFilters ->
                                RestaurantCard(
                                    restaurant = restaurantWithFilters.restaurant,
                                    filters = restaurantWithFilters.filterNames,
                                    onRestaurantClick = { onRestaurantClick(restaurantWithFilters.restaurant) },
                                )
                            }
                        }
                    }
            })
        }
}



