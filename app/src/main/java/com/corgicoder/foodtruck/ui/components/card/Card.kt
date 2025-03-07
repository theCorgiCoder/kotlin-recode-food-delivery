package com.corgicoder.foodtruck.ui.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.ui.components.cardInfo.CardInfo

@Composable
fun Card (
    restaurant: RestaurantData,
    filters: List<String>,
    showRating: Boolean,
    onRestaurantClick: () -> Unit,
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onRestaurantClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    ){
        CardInfo(
            restaurant = restaurant,
            filters = filters,
            showRating = showRating, // Always show rating on card
        )
    }
}