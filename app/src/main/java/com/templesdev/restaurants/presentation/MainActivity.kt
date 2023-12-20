package com.templesdev.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.templesdev.restaurants.presentation.navigation.RestaurantNav
import com.templesdev.restaurants.ui.theme.RestaurantsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantApp()
        }
    }
}


// "firebase_url": "https://restaurants-b4e11-default-rtdb.firebaseio.com",

@Composable
fun RestaurantApp() {
    AppView {
        AppNavigation()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    RestaurantNav(navController = navController)
}

@Composable
fun AppView(screen: @Composable () -> Unit) {
    RestaurantsAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            screen()
        }
    }
}