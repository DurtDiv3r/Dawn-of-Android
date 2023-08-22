package com.islaharper.dawnofandroid.presentation.screens.login

import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.navigation.Screen
import com.islaharper.dawnofandroid.presentation.common.GoogleButton
import com.islaharper.dawnofandroid.presentation.components.MessageBar
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.util.Resource

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val launchOneTapState by loginViewModel.launchOneTapSignIn.collectAsState()
    val messageBarState by loginViewModel.messageBarState.collectAsState()
    val navigationState by loginViewModel.navigationState.collectAsState()
    val oneTapSignInResponse by loginViewModel.oneTapSignInResponse.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.surface,
        content = { contentPadding ->
            LoginContent(
                modifier = modifier.padding(contentPadding),
                signedInState = launchOneTapState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    loginViewModel.onGoogleButtonClick()
                },
            )
        },
    )

    val authLauncher = authLauncher(loginViewModel)

    if (launchOneTapState && oneTapSignInResponse is Resource.Success) {
        (oneTapSignInResponse as Resource.Success<BeginSignInResult>).data.let { beginSignInResult ->
            LaunchedEffect(key1 = beginSignInResult) {
                val intent = IntentSenderRequest.Builder(beginSignInResult.pendingIntent.intentSender).build()
                authLauncher.launch(intent)
            }
        }
    }

    if (navigationState) {
        loginViewModel.onNavigateToHomeScreen()
        navigateToHomeScreen(navController = navHostController)
    }
}

@Composable
private fun authLauncher(loginViewModel: LoginViewModel) =
    rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult(),
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                loginViewModel.onOneTapSignInSuccess(result = result.data)
            } else {
                loginViewModel.onOneTapSignInFailure(failureMessage = "Error Signing In")
            }
        } catch (e: ApiException) {
            loginViewModel.onOneTapSignInFailure(
                failureMessage = e.message ?: "Exception Signing In",
            )
        }
    }

private fun navigateToHomeScreen(navController: NavHostController) {
    navController.navigate(route = Screen.Home.route) {
        popUpTo(route = Screen.Login.route) {
            inclusive = true
        }
    }
}

@Composable
private fun LoginContent(
    signedInState: Boolean,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = modifier
                .weight(1f),
        ) {
            if (signedInState) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            } else {
                MessageBar(messageBarState = messageBarState)
            }
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CentralContent(
                signInState = signedInState,
                onButtonClicked = onButtonClicked,
            )
        }
    }
}

@Composable
private fun CentralContent(
    signInState: Boolean,
    onButtonClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(120.dp),
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = stringResource(R.string.google_logo),
        )
        Text(
            text = stringResource(R.string.sign_in_welcome),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = stringResource(R.string.sign_in_description),
            modifier = Modifier
                .padding(bottom = 40.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        GoogleButton(
            loadingState = signInState,
            onClick = onButtonClicked,
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LoginContentPreview() {
    DawnOfAndroidTheme {
        Surface {
            LoginContent(
                signedInState = false,
                messageBarState = MessageBarState.Idle,
                onButtonClicked = {},
            )
        }
    }
}
