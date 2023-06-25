package com.islaharper.dawnofandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.islaharper.dawnofandroid.navigation.DawnNavHost
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel by viewModels()
        val isDarkMode by mainViewModel.darkModeState
        val isDynamicTheme by mainViewModel.dynamicThemeState

        setContent {
            DawnOfAndroidTheme(
                darkTheme = isDarkMode,
                dynamicColor = isDynamicTheme,
            ) {
                navHostController = rememberNavController()
                DawnNavHost(
                    navController = navHostController,
                    onThemeUpdated = {
                        mainViewModel.saveDarkModeState(isDarkMode = !isDarkMode)
                    },
                    onDynamicUpdated = {
                        mainViewModel.saveDynamicThemeState(isDynamicTheme = !isDynamicTheme)
                    },
                )
            }
        }
    }
}
