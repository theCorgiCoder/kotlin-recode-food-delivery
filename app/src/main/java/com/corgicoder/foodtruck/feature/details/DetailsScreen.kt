package com.corgicoder.foodtruck.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.cardInfo.CardInfo

@Composable
fun DetailsScreen(
    restaurantId: String,
    onNavigateBack: () -> Unit,
) {
    val viewModel: DetailsViewModel = viewModel()
    val restaurant by viewModel.restaurant.collectAsState()

    LaunchedEffect(restaurantId) {
        viewModel.loadRestaurant(restaurantId)
    }

    Column {
        BackButton(
            onNavigateBack = onNavigateBack,
            modifier = Modifier.padding(16.dp)
        )
        restaurant?.let {
            CardInfo(restaurant = it, showRating = false)

        }
    }
}

