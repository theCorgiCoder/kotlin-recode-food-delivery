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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corgicoder.foodtruck.R
import com.corgicoder.foodtruck.data.Restaurant
import com.corgicoder.foodtruck.ui.components.icon.CustomIcon
import com.corgicoder.foodtruck.ui.components.icon.IconType
import com.corgicoder.foodtruck.ui.components.text.CustomText

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
                    IconRow(
                        rating = restaurant.rating,
                        icon = IconType.ImageVectorIcon(Icons.Default.Star),
                        iconTint = Color.Yellow,
                        fontWeight = FontWeight.Bold,
                        textColor = Color.Black,
                        fontSize = 18.dp
                        )
                }
            }
            TagRow(filterIds = restaurant.filterIds)

            if (showRating){
            val deliveryTime = restaurant.deliveryTimeMinutes
            IconRow(
                icon = IconType.DrawableResourceIcon(R.drawable.clock_icon),
                iconTint = Color.Red,
                fontWeight = FontWeight.Bold,
                textColor = Color.Black,
                fontSize = 18.dp,
                text = "$deliveryTime minutes"
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
    ) {
        CustomIcon(
            icon = icon,
            contentDescription = null,
            modifier = Modifier.size(28.dp).padding(end = 3.dp),
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
fun TagRow (filterIds: List<String>){
    Row (
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        filterIds.forEach { filterId ->
            Text(text = filterId)
        }
    }
}


