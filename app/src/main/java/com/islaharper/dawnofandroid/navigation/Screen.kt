package com.islaharper.dawnofandroid.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash_screen")
    object Welcome : Screen(route = "welcome_screen")
    object Login : Screen(route = "login_screen")
    object Home : Screen(route = "home_screen")
}
