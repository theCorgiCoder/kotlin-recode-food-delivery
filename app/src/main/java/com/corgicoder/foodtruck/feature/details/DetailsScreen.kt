package com.corgicoder.foodtruck.feature.details

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsScreen(
    onNavigateBack: () -> Unit,
){
    Box(){
        /*  RestaurantCard(
              restaurant = Restaurant,
              ) { }
              */
        Text(text = "Details Page")
        Button(onClick = (onNavigateBack)) {
            Text(text = "Go Back")
        }
    }
}

