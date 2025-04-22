package com.corgicoder.foodtruck.ui.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.R

@Composable
fun Header(modifier: Modifier = Modifier){

        Row (modifier = Modifier.padding(16.dp)){
            Image(
                painter = painterResource(R.drawable.umain_logo),
                contentDescription = "Image of umain logo",
                modifier = Modifier,
                alignment = Alignment.TopStart
            )
        }

}