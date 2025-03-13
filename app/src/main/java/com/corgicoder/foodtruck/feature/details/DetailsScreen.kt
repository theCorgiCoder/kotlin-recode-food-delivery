package com.corgicoder.foodtruck.feature.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.model.RestaurantData
import com.corgicoder.foodtruck.data.model.RestaurantOpenStatus
import com.corgicoder.foodtruck.data.model.RestaurantWithFilterNames
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.feature.home.HomeViewModel
import com.corgicoder.foodtruck.ui.components.button.BackButton
import com.corgicoder.foodtruck.ui.components.cardInfo.CardInfo

@Composable
fun DetailsScreen(
    restaurantId: String?,
    repository: RestaurantRepository,
    onNavigateBack: () -> Unit,
) {

    val restaurantsWithFilters = viewModel.restaurantsWithFilterNames.collectAsState().value
    val restaurantWithFilters = restaurantsWithFilters.find { it.restaurant.id == restaurantId }
    val openStatus = viewModel.openStatus.collectAsState()
    val isLoadingStatus = viewModel.isLoadingStatus.collectAsState()


    //fetch restaurant details
    LaunchedEffect(restaurantId) {
        try {
         openStatus = repository.fetchRestaurantStatus(restaurantId)
        } catch (e: Exception){

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            error != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        BackButton(
                            onNavigateBack = onNavigateBack,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Text("Error loading restaurant: $error")
                }
            }

            restaurantData != null -> {
                Column {
                    BackButton(
                        onNavigateBack = onNavigateBack,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    restaurantData.let {restaurant ->
                       /* CardInfo(
                            restaurantId = restaurant.id
                            restaurant = restaurant,
                            filters = filterNames,
                            showRating = showRating
                        )

                        */
                    }


                }
            }
        }
    }
}

