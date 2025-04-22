package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.corgicoder.foodtruck.ui.theme.Background
import com.corgicoder.foodtruck.ui.theme.DarkText
import com.corgicoder.foodtruck.ui.theme.LightText
import com.corgicoder.foodtruck.ui.theme.Selected

@Composable
fun FilterButton (
    filterId: String,
    imageUrl: String,
    name: String,
    selected: Boolean,
    onClick: (String?) -> Unit
) {

    val buttonHeight = 50.dp

    Surface(
        shape = RoundedCornerShape(24.dp), // Increased corner radius to match design
        color = if (selected) Selected else Background,
        border = null,
        shadowElevation = 4.dp,
        modifier = Modifier
            .height(buttonHeight) // Increased height to accommodate larger image
            .clickable { onClick(filterId) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .height(buttonHeight)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = if (selected) LightText else DarkText,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}