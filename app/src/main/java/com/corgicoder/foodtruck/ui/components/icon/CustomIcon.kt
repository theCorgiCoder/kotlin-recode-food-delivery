package com.corgicoder.foodtruck.ui.components.icon

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class IconType {
    data class ImageVectorIcon(val imageVector: ImageVector) : IconType()
    data class DrawableResourceIcon(val id: Int) : IconType()
}

@Composable
fun CustomIcon(
    icon: IconType,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    tint: Color? = null
){
    when (icon) {
        is IconType.ImageVectorIcon -> Icon(
            imageVector = icon.imageVector,
            contentDescription = contentDescription,
            modifier = modifier
                .size(size)
                .padding(end = 3.dp),
            tint = tint ?: Color.Unspecified
        )
        is IconType.DrawableResourceIcon ->  Icon(
            painter = painterResource(id = icon.id),
            contentDescription = contentDescription,
            modifier = modifier
                .size(size)
                .padding(end = 3.dp),
            tint = tint ?: Color.Unspecified
        )
    }

}