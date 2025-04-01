package com.corgicoder.foodtruck.feature.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.feature.home.HomeViewModel
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.card.Card

@Composable
fun DetailsScreen(
    restaurantId: String,
    detailsViewModel: DetailsViewModel,
    repository: RestaurantRepository,
    onNavigateBack: () -> Unit,
    showRating: Boolean,
) {

    // Collect open status from DetailsViewModel

    val status = detailsViewModel.detailedState.collectAsState().value.openStatus?.isCurrentlyOpen
    val restaurantDetails = detailsViewModel.detailedState.collectAsState().value.selectedRestaurant




    //fetch restaurant details
    LaunchedEffect(restaurantId) {
      detailsViewModel.fetchOpenStatus(restaurantId)
    }
    Column {
        Text(text = "$status")
        Text(text = "$restaurantDetails")
    }


}




