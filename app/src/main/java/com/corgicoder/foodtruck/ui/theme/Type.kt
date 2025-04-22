package com.corgicoder.foodtruck.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.corgicoder.foodtruck.R

val titleOne = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 18.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

val titleTwo = TextStyle(
    fontFamily = FontFamily(Font(resId = R.font.poppins_regular)),
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp
)

val subtitleOne = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

val footerOne = TextStyle(
    fontFamily = FontFamily(Font(resId = R.font.inter_variablefont)),
    fontWeight = FontWeight(500),
    fontSize = 10.sp,
    letterSpacing = 0.5.sp
)

val headlineOne = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 24.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

val headlineTwo = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 16.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = titleOne,
    titleMedium = titleTwo,
    titleSmall = subtitleOne,
    headlineLarge = headlineOne,
    headlineSmall = headlineTwo,
    labelSmall = footerOne,


)