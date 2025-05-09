package com.corgicoder.foodtruck.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.corgicoder.foodtruck.R
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.ui.components.filter.FiltersRow
import com.corgicoder.foodtruck.ui.components.icon.CustomIcon
import com.corgicoder.foodtruck.ui.components.icon.IconType
import com.corgicoder.foodtruck.ui.components.text.CustomText
import com.corgicoder.foodtruck.ui.theme.Background
import com.corgicoder.foodtruck.ui.theme.DarkText
import com.corgicoder.foodtruck.ui.theme.Negative
import com.corgicoder.foodtruck.ui.theme.SubtitleText
import com.corgicoder.foodtruck.ui.theme.Yellow

@Composable
fun RestaurantCard (
    restaurant: RestaurantData,
    filters: List<String>,
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
                .fillMaxSize()
                .background(Background)

        ) {
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = "Image of ${restaurant.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 7f),
                alignment = Alignment.TopStart
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconRow(
                        rating = restaurant.rating,
                        icon = IconType.ImageVectorIcon(Icons.Default.Star),
                        iconTint = Yellow,
                        fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                        textColor = DarkText,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize
                    )
                }


                FiltersRow(filters = filters)

                IconRow(
                    icon = IconType.DrawableResourceIcon(R.drawable.clock_icon),
                    iconTint = Negative,
                    fontWeight = MaterialTheme.typography.labelSmall.fontWeight,
                    textColor = SubtitleText,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    text = "${restaurant.deliveryTimeMinutes} minutes"
                )
            }
        }
    }
}


@Composable
private fun IconRow(
    rating: Float? = null,
    icon: IconType,
    iconTint: Color,
    text: String? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = FontWeight.Normal,
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
