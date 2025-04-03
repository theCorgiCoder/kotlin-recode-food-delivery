package com.corgicoder.foodtruck.ui.components.card

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.corgicoder.foodtruck.data.model.RestaurantData

@Composable
fun DetailsCard (
    restaurant: RestaurantData,
    filters: List<String>,
    openStatus: Boolean,
    modifier: Modifier,
    ) {
    Column (
        modifier = modifier
    ) {
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = "Image of ${restaurant.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                alignment = Alignment.TopStart
            )
            Card (modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(1f)
                    )
                    FiltersRow(filters = filters)
                    if (openStatus) {
                        Text(
                            text = "Open"
                        )
                    } else {
                        Text(
                            text = "Closed"
                        )
                    }

                }
            }

        }
}

@Composable
fun FiltersRow(filters: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        filters.forEachIndexed { index, filter ->
            Text(
                text = filter,
                modifier = Modifier.padding(end = 4.dp)
            )

            // Add bullet circle between items (not after the last item)
            if (index < filter.length - 1) {
                Text(
                    text = " • ", // Bullet with spaces on both sides
                )
            }
        }
    }
}


