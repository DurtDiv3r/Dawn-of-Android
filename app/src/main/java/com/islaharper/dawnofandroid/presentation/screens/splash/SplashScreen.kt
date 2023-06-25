package com.islaharper.dawnofandroid.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.navigation.Screen

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val onBoardingComplete by splashViewModel.onBoardingCompleted.collectAsState()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.jetpack))
    val progress by animateLottieCompositionAsState(composition = composition)

    LaunchedEffect(key1 = progress) {
        if (progress == 1f) {
            navHostController.popBackStack()
            if (onBoardingComplete) {
                navHostController.navigate(Screen.Login.route)
            } else {
                navHostController.navigate(Screen.Welcome.route)
            }
        }
    }
    if (composition != null) {
        Splash(composition, progress)
    }
}

@Composable
private fun Splash(composition: LottieComposition?, progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
            )
        }
    }
}

@Preview
@Composable
fun SplashPreview() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.jetpack))
    val progress by animateLottieCompositionAsState(composition = composition)
    if (composition != null) {
        Splash(composition, progress)
    }
}
