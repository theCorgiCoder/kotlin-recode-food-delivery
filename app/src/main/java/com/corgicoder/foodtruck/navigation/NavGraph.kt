package com.corgicoder.foodtruck.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.feature.details.DetailsScreen
import com.corgicoder.foodtruck.feature.home.HomeScreen
import com.corgicoder.foodtruck.feature.home.HomeViewModel

object Route {
    const val HOME = "home"
    const val DETAILS = "details/{restaurantId}"

    fun createRestaurantDetailsRoute(restaurantId: String) = "details/$restaurantId"
}

@Composable
fun NavGraph (
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val restaurantRepository = RestaurantRepository()

    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        composable(Route.HOME) {
            HomeScreen(
                //onFilterClick = {},
                onRestaurantClick = { restaurant ->
                    navController.navigate(Route.createRestaurantDetailsRoute(restaurant.id))

                    // Store the selected restaurant in the ViewModel
                    homeViewModel.setSelectedRestaurant(restaurant)
                },
                viewModel = homeViewModel
            )
        }
            composable (
                route = Route.DETAILS,
                arguments = listOf(navArgument("restaurantId") { type = NavType.StringType})
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId") ?: return@composable

                // Get the selected restaurant from the ViewModel
                val selectedRestaurant = homeViewModel.selectedRestaurant.collectAsState().value

                if (selectedRestaurant != null && selectedRestaurant.id == restaurantId) {
                    DetailsScreen(
                        restaurantId = restaurantId,
                        repository = restaurantRepository,
                        onNavigateBack = { navController.popBackStack() },
                    )
                } else {
                    Text("Error: Restaurant not found")
                }
            }
        }
}

