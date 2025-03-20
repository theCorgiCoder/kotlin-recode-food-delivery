package com.corgicoder.foodtruck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        .fillMaxWidth()
                ){
                    Box( modifier = Modifier) {
                        NavGraph(
                            homeViewModel = HomeViewModel()
                        )
                    }
            }
        }
            }
        }
    }



