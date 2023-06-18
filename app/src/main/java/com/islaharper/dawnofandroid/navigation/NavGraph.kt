package com.islaharper.dawnofandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.islaharper.dawnofandroid.presentation.screens.login.LoginScreen
import com.islaharper.dawnofandroid.presentation.screens.splash.SplashScreen
import com.islaharper.dawnofandroid.presentation.screens.welcome.WelcomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navHostController = navController)
        }
    }
}
