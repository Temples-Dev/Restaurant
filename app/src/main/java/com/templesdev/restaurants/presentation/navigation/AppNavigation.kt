package com.templesdev.restaurants.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.templesdev.restaurants.presentation.detail.RestaurantDetailViewModel
import com.templesdev.restaurants.presentation.detail.RestaurantDetailsScreen
import com.templesdev.restaurants.presentation.restaurants.RestaurantScreen
import com.templesdev.restaurants.presentation.restaurants.RestaurantViewModel

@Composable
fun RestaurantNav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Restaurant.route) {

        composable(route = Routes.Restaurant.route) {
            val viewModel: RestaurantViewModel = hiltViewModel()
            RestaurantScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                    navController.navigate(Routes.Restaurant.withArgs("$id"))
                },
            ) { id: Int, oldValue: Boolean ->
                viewModel.toggleFavorite(id, oldValue)
            }
        }

        composable(
            route = Routes.Restaurant.withArgs("{restaurant_id}"),
            arguments = listOf(navArgument("restaurant_id") {
                type = NavType.IntType
            })
        ) {
            val viewModel: RestaurantDetailViewModel = hiltViewModel()
            RestaurantDetailsScreen(viewModel.state.value)
        }
    }
}

//            navStackEntry ->
//            val id = navStackEntry.arguments?.getInt("restaurant_id")