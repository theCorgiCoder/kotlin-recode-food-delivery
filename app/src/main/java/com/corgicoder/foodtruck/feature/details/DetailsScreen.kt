package com.corgicoder.foodtruck.feature.details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.data.repository.FilterRepositoryImpl
import com.corgicoder.foodtruck.data.repository.RestaurantRepositoryImpl
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.card.DetailsCard


@Composable
fun DetailsScreen(
    restaurantId: String,
    onNavigateBack: () -> Unit,
) {

    val detailsViewModel: DetailsViewModel = viewModel {
        DetailsViewModel(
            repository = RestaurantRepositoryImpl(),
            filterRepository = FilterRepositoryImpl()
        )
    }

    val status = detailsViewModel.detailedState.collectAsState().value.openStatus?.isCurrentlyOpen
    val restaurantDetails = detailsViewModel.detailedState.collectAsState().value.selectedRestaurant
    val uiState = detailsViewModel.uiState.collectAsState()
    val namedFilters = detailsViewModel.detailedState.collectAsState().value.namedFilters

    //fetch restaurant details
    LaunchedEffect(restaurantId) {
        detailsViewModel.fetchRestaurantById(restaurantId)
    }

    Box(modifier = Modifier
        .fillMaxSize()
    ) {

        when {
            uiState.value.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.value.error != null -> {
                Log.d("DetailsScreen", "Showing error: ${uiState.value.error}")

            }

            else -> {
                Box{
                    BackButton(
                        onNavigateBack = onNavigateBack,
                        modifier = Modifier
                            .padding(
                                top = 32.dp,
                                bottom = 16.dp,
                                start = 16.dp,
                                end = 16.dp)
                            .zIndex(1f),
                    )

                    restaurantDetails?.let { restaurant ->

                        DetailsCard(
                            restaurant = restaurant,
                            filters = namedFilters,
                            openStatus = status ?: false,
                            modifier = Modifier.zIndex(0f)
                        )
                    } ?: run {
                        Log.d("DetailsScreen", "Showing error: ${uiState.value.error}")
                    }
                    }


                }

            }
        }
    }







