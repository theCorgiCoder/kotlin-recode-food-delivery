package com.corgicoder.foodtruck.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.corgicoder.foodtruck.R

val PoppinsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal)
)

@OptIn(ExperimentalTextApi::class)
val InterFamily = FontFamily(
    Font(
        resId = R.font.inter_variablefont,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400)
        )
)
)
