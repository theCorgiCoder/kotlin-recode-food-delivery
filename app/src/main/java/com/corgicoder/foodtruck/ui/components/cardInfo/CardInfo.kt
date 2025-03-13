package com.corgicoder.foodtruck.ui.components.cardInfo

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
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
fun CardInfo (
    restaurantId: String,
    restaurant: RestaurantData,
    filters:  List<String>,
    showRating: Boolean,
) {
    Column (
    ){
        AsyncImage(
            model = restaurant.imageUrl,
            contentDescription = "Image of ${restaurant.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                )

                if (showRating) {
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

            if (showRating && restaurant.deliveryTimeMinutes != null){
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
    Row (
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
fun TagRow (filterNames: List<String>){
    Row (
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


