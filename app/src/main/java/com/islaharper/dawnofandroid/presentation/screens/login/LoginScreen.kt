package com.islaharper.dawnofandroid.presentation.screens.login

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.navigation.Screen
import com.islaharper.dawnofandroid.presentation.common.StartActivityForResult
import com.islaharper.dawnofandroid.presentation.common.signIn
import com.islaharper.dawnofandroid.util.Resource

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val signedInState by loginViewModel.signedInState.collectAsState()
    val messageBarState by loginViewModel.messageBarState.collectAsState()
    val apiResponse by loginViewModel.apiResponse.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.surface,
        content = { contentPadding ->
            LoginContent(
                modifier = Modifier.padding(contentPadding),
                signedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    loginViewModel.saveSignedInState(signedIn = true)
                },
            )
        },
    )
    val activity = LocalContext.current as Activity

    StartActivityForResult(
        key = signedInState,
        onResultReceived = { tokenId ->
            loginViewModel.verifyToken(
                apiRequest = ApiTokenRequest(tokenId = tokenId),
            )
        },
        onDialogDismissed = {
            loginViewModel.saveSignedInState(signedIn = false)
        },
    ) { activityLauncher ->

        if (signedInState) {
            signIn(
                activity = activity,
                launchActivityResult = { intentSenderRequest ->
                    activityLauncher.launch(intentSenderRequest)
                },
                accountNotFound = {
                    loginViewModel.saveSignedInState(signedIn = false)
                    loginViewModel.updateMessageBarErrorState("Google Account not found")
                },
            )
        }
    }

    LaunchedEffect(key1 = apiResponse) {
        when (apiResponse) {
            is Resource.Success -> {
                val response = (apiResponse as Resource.Success<ApiResponse>).data.success
                if (response) {
                    navigateToHomeScreen(navController = navHostController)
                }
                loginViewModel.saveSignedInState(signedIn = false)
            }

            else -> {
                loginViewModel.saveSignedInState(signedIn = false)
            }
        }
    }
}

private fun navigateToHomeScreen(
    navController: NavHostController,
) {
    navController.navigate(route = Screen.Home.route) {
        popUpTo(route = Screen.Login.route) {
            inclusive = true
        }
    }
}
