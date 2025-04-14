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
import com.corgicoder.foodtruck.data.repository.FilterRepository
import com.corgicoder.foodtruck.data.repository.FilterRepositoryImpl
import com.corgicoder.foodtruck.data.repository.RestaurantRepository
import com.corgicoder.foodtruck.data.repository.RestaurantRepositoryImpl
import com.corgicoder.foodtruck.feature.details.DetailsScreen
import com.corgicoder.foodtruck.feature.details.DetailsViewModel
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
    detailsViewModel: DetailsViewModel,
) {
    val navController = rememberNavController()
    val restaurantRepository = RestaurantRepositoryImpl()

    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        composable(Route.HOME) {
            HomeScreen(
                onRestaurantClick = { restaurant ->
                    navController.navigate(Route.createRestaurantDetailsRoute(restaurant.id))

                    // Store the selected restaurant in the ViewModel
                homeViewModel.selectedRestaurant(restaurant)
                },
                viewModel = homeViewModel
            )
        }
            composable (
                route = Route.DETAILS,
                arguments = listOf(navArgument("restaurantId") { type = NavType.StringType})
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId") ?: return@composable

                    DetailsScreen(
                        restaurantId = restaurantId,
                        onNavigateBack = { navController.popBackStack() },
                        detailsViewModel = detailsViewModel,
                    )
            }
        }
}

