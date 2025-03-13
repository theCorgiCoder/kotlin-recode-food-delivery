package com.corgicoder.foodtruck.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.cardInfo.CardInfo

@Composable
fun DetailsScreen(
    restaurantId: String?,
    restaurant: RestaurantRepository,
    onNavigateBack: () -> Unit,
    showRating: Boolean,
) {


    Column {
        BackButton(
            onNavigateBack = onNavigateBack,
            modifier = Modifier.padding(16.dp)
        )


    }
}

