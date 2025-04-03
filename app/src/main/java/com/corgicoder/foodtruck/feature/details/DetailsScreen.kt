package com.corgicoder.foodtruck.feature.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.feature.home.HomeViewModel
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.card.Card
import com.corgicoder.foodtruck.ui.components.card.DetailsCard


@Composable
fun DetailsScreen(
    restaurantId: String,
    detailsViewModel: DetailsViewModel,
    repository: RestaurantRepository,
    onNavigateBack: () -> Unit,
) {

    // Collect open status from DetailsViewModel

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
                Box(
                    modifier = Modifier,

                ) {

                    BackButton(
                        onNavigateBack = onNavigateBack,
                        modifier = Modifier.padding(16.dp).background(Color.Blue).zIndex(1f)
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







