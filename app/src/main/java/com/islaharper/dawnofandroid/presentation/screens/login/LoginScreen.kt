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
import com.google.android.gms.common.api.CommonStatusCodes
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.navigation.Screen
import com.islaharper.dawnofandroid.presentation.common.GoogleButton
import com.islaharper.dawnofandroid.presentation.components.MessageBar
import com.islaharper.dawnofandroid.util.Resource
import com.islaharper.dawnofandroid.util.Resource.Success

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
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
                    loginViewModel.oneTapSignIn()
                }
            )
        }
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                loginViewModel.verifyToken(intent = result.data)
            } else {
                loginViewModel.saveSignedInState(signedIn = false)
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    loginViewModel.saveSignedInState(signedIn = false)
                }

                CommonStatusCodes.NETWORK_ERROR -> {
                    loginViewModel.saveSignedInState(signedIn = false)
                }

                else -> {
                    loginViewModel.saveSignedInState(signedIn = false)
                }
            }
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )

    LaunchedEffect(key1 = apiResponse) {
        when (apiResponse) {
            is Success -> {
                val response = (apiResponse as Success<ApiResponse>).data.success
                if (response) {
                    navigateToHomeScreen(navController = navHostController)
                    loginViewModel.saveSignedInState(signedIn = false)
                } else {
                    loginViewModel.saveSignedInState(signedIn = false)
                }
            }

            else -> {}
        }
    }
}

@Composable
fun OneTapSignIn(
    viewModel: LoginViewModel = hiltViewModel(),
    launch: (result: BeginSignInResult) -> Unit
) {
    when (val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Resource.Idle,
        is Resource.Error -> null

        is Success -> oneTapSignInResponse.data.let {
            LaunchedEffect(key1 = it) {
                launch(it)
            }
        }
    }
}

private fun navigateToHomeScreen(
    navController: NavHostController
) {
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
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            if (signedInState) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CentralContent(
                modifier = modifier,
                signInState = signedInState,
                onButtonClicked = onButtonClicked
            )
        }
    }
}

@Composable
private fun CentralContent(
    signInState: Boolean,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .size(120.dp),
        painter = painterResource(id = R.drawable.ic_google_logo),
        contentDescription = stringResource(R.string.google_logo)
    )
    Text(
        text = stringResource(R.string.sign_in_welcome),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.headlineLarge
    )
    Text(
        text = stringResource(R.string.sign_in_description),
        modifier = Modifier
            .padding(bottom = 40.dp, top = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
    GoogleButton(
        loadingState = signInState,
        onClick = onButtonClicked
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginContentPreview() {
    LoginContent(
        signedInState = false,
        messageBarState = MessageBarState.Idle,
        onButtonClicked = {}
    )
}
