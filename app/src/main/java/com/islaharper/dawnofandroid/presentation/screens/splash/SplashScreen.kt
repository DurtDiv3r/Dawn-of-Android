package com.islaharper.dawnofandroid.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.navigation.Screen
import com.islaharper.dawnofandroid.ui.theme.SPLASH_SPACER_HEIGHT
import com.islaharper.dawnofandroid.ui.theme.infoGreen

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
    Splash(composition, progress)
}

@Composable
private fun Splash(composition: LottieComposition?, progress: Float) {
    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(composition = composition, progress = progress)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(SPLASH_SPACER_HEIGHT))
            Text(
                text = stringResource(R.string.app_name),
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.infoGreen,
            )
        }
    }
}

@Preview
@Composable
fun SplashPreview() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.jetpack))
    val progress by animateLottieCompositionAsState(composition = composition)
    Splash(composition, progress)
}
