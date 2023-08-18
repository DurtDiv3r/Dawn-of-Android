package com.islaharper.dawnofandroid.presentation.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.ui.theme.Shapes
import com.islaharper.dawnofandroid.util.Constants
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onGoogleSignInCompleted: (Intent) -> Unit,
    onError: (String) -> Unit
) {
    val primaryText = stringResource(R.string.sign_in_with_google)
    val secondaryText = stringResource(R.string.please_wait)

    var buttonText by remember { mutableStateOf(primaryText) }
    var buttonState by remember { mutableStateOf(false) }

    val signInScope = rememberCoroutineScope()

    val authScope = rememberCoroutineScope()
    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                authScope.launch {
                    result.data?.let { onGoogleSignInCompleted(it) }
                }
            } else {
                onError("Unable to Sign In")
            }
            buttonState = false
            buttonText = primaryText
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    Log.d("GoogleSignInButton", "One-tap dialog was closed.")
                    onError("Sign In Dismissed")
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("GoogleSignInButton", "One-tap encountered a network error.")
                    onError("Network Error")
                }
                else -> {
                    Log.d(
                        "GoogleSignInButton",
                        "Couldn't get credential from result." +
                            " (${e.localizedMessage})"
                    )
                    onError("Unexpected Error Signing In")
                }
            }

            buttonState = false
            buttonText = primaryText
        }
    }
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .clickable(enabled = !buttonState) {
                buttonState = true
                buttonText = secondaryText

                signInScope.launch {
                    when (val result = oneTapSignIn(context)) {
                        is Resource.Idle -> null
                        is Resource.Error -> {
                            buttonState = false
                            buttonText = primaryText
                            result.ex?.message?.let { onError(it) }
                        }

                        is Resource.Success -> {
                            result.data.let {
                                val intent = IntentSenderRequest.Builder(
                                    it.pendingIntent.intentSender
                                ).build()
                                authLauncher.launch(intent)
                            }
                        }
                    }
                }
            },
        shape = Shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = stringResource(id = R.string.google_logo),
                tint = Color.Unspecified
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(
                text = buttonText,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.labelLarge
            )
            if (buttonState) {
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

suspend fun oneTapSignIn(context: Context): Resource<BeginSignInResult> {
    val oneTapClient = Identity.getSignInClient(context)

    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(Constants.CLIENT_ID)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    val signUpRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(Constants.CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    return try {
        val signInResult = oneTapClient.beginSignIn(signInRequest).await()
        Resource.Success(signInResult)
    } catch (ex: Exception) {
        try {
            val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
            Resource.Success(signUpResult)
        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GoogleButtonPreview() {
    GoogleSignInButton(onGoogleSignInCompleted = {}) {}
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GoogleButtonLoadingPreview() {
    GoogleSignInButton(
        onGoogleSignInCompleted = {}
    ) {}
}
