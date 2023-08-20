package com.islaharper.dawnofandroid.presentation.screens.login

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.navigation.Screen
import com.islaharper.dawnofandroid.presentation.common.GoogleSignInButton
import com.islaharper.dawnofandroid.presentation.components.MessageBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val messageBarState by loginViewModel.messageBarState.collectAsState()
    val navigationState by loginViewModel.navigationState.collectAsState()
    val apiResponse by loginViewModel.apiResponse.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.surface,
        content = { contentPadding ->
            LoginContent(
                modifier = modifier.padding(contentPadding),
                messageBarState = messageBarState,
                onSignInComplete = { intent ->
                    loginViewModel.verifyToken(intent)
                },
                onError = { errorMessage ->
                    loginViewModel.updateMessageBarErrorState(errorMessage)
                }
            )
        }
    )

    LaunchedEffect(key1 = apiResponse) {
        if (navigationState) {
            launch {
                // Allow time to display success messageBar
                delay(1000L)
                navigateToHomeScreen(navController = navHostController)
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
    messageBarState: MessageBarState,
    onSignInComplete: (Intent) -> Unit,
    onError: (String) -> Unit,
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
            MessageBar(messageBarState = messageBarState)
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
                onSignInComplete = onSignInComplete,
                onError = onError
            )
        }
    }
}

@Composable
private fun CentralContent(
    onSignInComplete: (Intent) -> Unit,
    onError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
        GoogleSignInButton(
            onGoogleSignInCompleted = onSignInComplete,
            onError = onError
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginContentPreview() {
    LoginContent(
        messageBarState = MessageBarState.Idle,
        onSignInComplete = {},
        onError = {}
    )
}
