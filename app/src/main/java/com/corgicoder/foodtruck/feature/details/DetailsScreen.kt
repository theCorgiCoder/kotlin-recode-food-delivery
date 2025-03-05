package com.corgicoder.foodtruck.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.corgicoder.foodtruck.feature.home.HomeViewModel
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.cardInfo.CardInfo

@Composable
fun DetailsScreen(
    restaurantId: String?,
    onNavigateBack: () -> Unit,
    showRating: Boolean,
) {
    val viewModel: HomeViewModel = viewModel()
    val restaurants = viewModel.restaurants
    val filters = viewModel.filtersData

    val restaurant = restaurants.find { it.id == restaurantId }

    Column {
        BackButton(
            onNavigateBack = onNavigateBack,
            modifier = Modifier.padding(16.dp)
        )
        restaurant?.let {
            CardInfo(restaurant = it, showRating = showRating, filters = filters)
        } ?: run {
            Text(text = "Restaurant not found")
        }
    }
}

