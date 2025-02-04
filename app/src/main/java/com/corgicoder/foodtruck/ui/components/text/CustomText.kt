package com.corgicoder.foodtruck.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomText(
    text: String,
    textColor: Color = Color.Unspecified,
    fontSize: Dp,
    fontWeight: FontWeight? = FontWeight.Normal,
    modifier: Modifier = Modifier

){
    Text (
        text = text,
        color = textColor,
        fontSize = fontSize.value.sp,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}