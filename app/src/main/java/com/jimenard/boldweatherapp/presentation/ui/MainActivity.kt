package com.jimenard.boldweatherapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jimenard.boldweatherapp.presentation.ui.screens.locationDetail.DetailScreen
import com.jimenard.boldweatherapp.presentation.ui.screens.search.SearchScreen
import com.jimenard.boldweatherapp.presentation.ui.screens.splash.SplashScreen
import com.jimenard.boldweatherapp.presentation.ui.theme.BoldWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoldWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "searchScreen") {
        composable("splashScreen") {
            SplashScreen(navController)
        }
        composable("searchScreen") {
            SearchScreen(navController)
        }
        composable(
            "detailScreen/{locationName}",
            arguments = listOf(navArgument("locationName") { type = NavType.StringType }),
        ) { backStackEntry ->
            val locationName = backStackEntry.arguments?.getString("locationName").orEmpty()
            DetailScreen(locationName)
        }
    }
}
