package com.islaharper.dawnofandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.islaharper.dawnofandroid.presentation.screens.splash.SplashScreen

@Composable
fun DawnNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navController)
        }
    }
}
