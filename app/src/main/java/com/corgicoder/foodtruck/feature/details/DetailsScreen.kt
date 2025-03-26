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
    homeViewModel: HomeViewModel,
    repository: RestaurantRepository,
    onNavigateBack: () -> Unit,
    showRating: Boolean,
) {

   val detailsViewModel: DetailsViewModel = viewModel(
       factory = DetailsViewModelFactory(repository)
   )
/*
    // Find the restaurant in the HomeViewModel's data
    val restaurantsWithFilters = homeViewModel.restaurantsWithFilterNames.collectAsState().value
    val restaurantWithFilters = restaurantsWithFilters.find { it.restaurant.id == restaurantId }

    // Collect open status from DetailsViewModel
    val openStatus = detailsViewModel.openStatus.collectAsState()
    val isLoadingStatus = detailsViewModel.isLoadingStatus.collectAsState()
    val statusError = detailsViewModel.statusError.collectAsState()

    println("OPEN STATUS: $openStatus")


    //fetch restaurant details
    LaunchedEffect(restaurantId) {
      detailsViewModel.fetchOpenStatus(restaurantId)
    }
// This is redundant, restaurants should never = null
    if (restaurantWithFilters == null) {
        Box(modifier = Modifier.fillMaxSize()){
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                BackButton(
                    onNavigateBack = onNavigateBack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Restaurant not found")
            }
        }
    } else {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                BackButton(
                    onNavigateBack = onNavigateBack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
    }

        }

 */
}




