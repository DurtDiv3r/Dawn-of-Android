package com.islaharper.dawnofandroid.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash_screen")
}
