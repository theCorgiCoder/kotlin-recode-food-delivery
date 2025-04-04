package com.corgicoder.foodtruck.ui.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.corgicoder.foodtruck.R
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.ui.components.icon.CustomIcon
import com.corgicoder.foodtruck.ui.components.icon.IconType
import com.corgicoder.foodtruck.ui.components.text.CustomText

@Composable
fun Card (
    restaurant: RestaurantData,
    filters: List<String>,
    showRating: Boolean,
    onRestaurantClick: () -> Unit,

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onRestaurantClick)
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                    Column (modifier = Modifier.padding(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = restaurant.name,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.weight(1f)
                            )
                                IconRow(
                                    rating = restaurant.rating,
                                    icon = IconType.ImageVectorIcon(Icons.Default.Star),
                                    iconTint = Color.Yellow,
                                    fontWeight = FontWeight.Bold,
                                    textColor = Color.Black,
                                    fontSize = 16.dp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        TagRow(filterNames = filters)

                        Spacer(modifier = Modifier.height(8.dp))
                            IconRow(
                                icon = IconType.DrawableResourceIcon(R.drawable.clock_icon),
                                iconTint = Color.Red,
                                fontWeight = FontWeight.Medium,
                                textColor = Color.Black,
                                fontSize = 14.dp,
                                text = "${restaurant.deliveryTimeMinutes} minutes"
                            )
                    }
                }
        }


@Composable
private fun IconRow(
    rating: Float? = null,
    icon: IconType,
    iconTint: Color,
    text: String? = null,
    fontSize: Dp,
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = Color.Unspecified
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        CustomIcon(
            icon = icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 4.dp),
            tint = iconTint
        )
        CustomText(
            text = text ?: rating?.toString() ?: "",
            textColor = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
        )
    }
}

@Composable
fun TagRow(filterNames: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        filterNames.forEachIndexed { index, filterName ->
            Text(
                text = filterName,
                modifier = Modifier.padding(end = 4.dp)
            )

            // Add bullet circle between items (not after the last item)
            if (index < filterNames.size - 1) {
                Text(
                    text = " • ", // Bullet with spaces on both sides
                )
            }
        }
    }
}