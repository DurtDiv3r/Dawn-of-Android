package com.islaharper.dawnofandroid.presentation.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onThemeUpdated: () -> Unit,
    onDynamicUpdated: () -> Unit,
) {
    Text(text = "Homescreen Placeholder")
}
