package com.corgicoder.foodtruck.ui.components.cardInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardInfo (
    restaurant: Restaurant,
    showRating: Boolean = true
) {
    Column (
        modifier = Modifier.height(300.dp),
    ){
        Image(
            painter = painterResource(id = restaurant.imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                )

                if (showRating) {
                    RatingRow(restaurant.rating)
                }
            }
            TagRow(filterIds = restaurant.filterIds)
            val deliveryTime = restaurant.deliveryTimeMinutes
            Text(text = "$deliveryTime minutes")
        }
    }

}
@Composable
private fun RatingRow(rating: Float) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.size(28.dp).padding(end = 3.dp),
            tint = Color.Yellow
        )
        Text(
            text = "$rating",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun TagRow (filterIds: List<String>){
    Row (
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        filterIds.forEach { filterId ->
            Text(text = filterId)
        }
    }
}


