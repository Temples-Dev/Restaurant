package temples.dev.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import temples.dev.restaurants.presentation.details.RestaurantDetailsScreen
import temples.dev.restaurants.presentation.list.RestaurantsScreen
import temples.dev.restaurants.presentation.list.RestaurantsViewModel
import temples.dev.ui.theme.RestaurantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                RestaurantsApp()
            }
        }
    }
}

@Composable
private fun RestaurantsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "restaurants") {
        composable(route = "restaurants") {

            val viewModel: RestaurantsViewModel = viewModel()

            RestaurantsScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                navController.navigate("restaurants/$id")
            }) { id, oldValue ->
                viewModel.toggleFavorite(id, oldValue)
            }



    }
    composable(
        route = "restaurants/{restaurant_id}",
        arguments = listOf(navArgument("restaurant_id") {
            type = NavType.IntType
        }),
        deepLinks = listOf(navDeepLink {
            uriPattern = "www.restaurant.details.com/{restaurant_id}"
        })
    ) { navStackEntry ->
        val id = navStackEntry.arguments?.getInt("restaurant_id")
        RestaurantDetailsScreen()

    }
}

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    RestaurantTheme {
        Text(text = "The Restaurant App is in Progress!")
    }
}
