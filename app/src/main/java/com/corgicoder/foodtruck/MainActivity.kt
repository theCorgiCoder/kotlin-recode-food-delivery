package com.corgicoder.foodtruck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.repository.FilterRepository
import com.corgicoder.foodtruck.data.repository.FilterRepositoryImpl
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.data.repository.RestaurantRepositoryImpl
import com.corgicoder.foodtruck.feature.details.DetailsViewModel
import com.corgicoder.foodtruck.feature.home.HomeScreen
import com.corgicoder.foodtruck.feature.home.HomeViewModel
import com.corgicoder.foodtruck.navigation.NavGraph
import com.corgicoder.foodtruck.ui.theme.FoodTruckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodTruckTheme {
                Surface (
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(0xFFF8F8F8)
                ){
                    Box( modifier = Modifier) {
                        NavGraph(
                            homeViewModel = HomeViewModel(
                                filterRepository = FilterRepositoryImpl(),
                                restaurantRepository = RestaurantRepositoryImpl()
                            ),
                            detailsViewModel = DetailsViewModel(
                                repository = RestaurantRepositoryImpl()
                            )

                        )
                    }
            }
        }
            }
        }
    }



