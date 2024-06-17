package com.jimenard.boldweatherapp.presentation.ui.screens.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Suppress("ktlint:standard:function-naming")
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(100000)
        navController.navigate("searchScreen") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "BOLD WEATHER APP",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
        )
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}
