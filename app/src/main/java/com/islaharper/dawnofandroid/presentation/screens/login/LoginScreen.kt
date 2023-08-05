package com.islaharper.dawnofandroid.presentation.screens.login

import android.content.res.Configuration
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.presentation.common.GoogleButton
import com.islaharper.dawnofandroid.presentation.components.MessageBar

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val signedInState by loginViewModel.signedInState.collectAsState()
    val messageBarState by loginViewModel.messageBarState.collectAsState()
    val apiResponse by loginViewModel.apiResponse.collectAsState()
    val oneTapClient = loginViewModel.getSignInClient(LocalContext.current)

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
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    signedInState: Boolean,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
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
            modifier = modifier
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
    Image(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .size(120.dp),
        painter = painterResource(id = R.drawable.ic_google_logo),
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

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginContentPreview() {
    LoginContent(
        signedInState = false,
        messageBarState = MessageBarState.Idle,
        onButtonClicked = {},
    )
}
