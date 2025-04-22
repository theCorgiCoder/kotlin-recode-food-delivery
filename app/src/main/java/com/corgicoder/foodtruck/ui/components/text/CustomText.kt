package com.corgicoder.foodtruck.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit


@Composable
fun CustomText(
    text: String,
    textColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = FontWeight.Normal,
){
    Text (
        text = text,
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
    )
}