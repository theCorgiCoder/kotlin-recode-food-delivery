package com.corgicoder.foodtruck.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.ui.components.filter.FiltersRow

@Composable
fun DetailsCard (
    restaurant: RestaurantData,
    filters: List<String>,
    openStatus: Boolean,
    modifier: Modifier,
    ) {
    Box (
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = restaurant.imageUrl,
            contentDescription = "Image of ${restaurant.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f),
            alignment = Alignment.TopStart
        )
        Box (
            modifier = Modifier.padding(16.dp)
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .offset(y = (LocalConfiguration.current.screenHeightDp.dp * 0.25f) - 40.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFFF8F8F8))
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = restaurant.name,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    FiltersRow(filters = filters)

                    Text(
                        text = if (openStatus) "Open" else "Closed",
                    )

                }
            }

        }
    }
}




