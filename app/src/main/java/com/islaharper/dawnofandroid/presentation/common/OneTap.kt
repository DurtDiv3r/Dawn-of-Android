package com.islaharper.dawnofandroid.presentation.common

import android.app.Activity
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.islaharper.dawnofandroid.util.Constants.CLIENT_ID

@Composable
fun StartActivityForResult(
    oneTapClient: SignInClient,
    key: Any,
    onResultReceived: (String) -> Unit,
    onDialogDismissed: () -> Unit,
    launcher: (ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>) -> Unit,
) {
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)
                val tokenId = credentials.googleIdToken

                if (tokenId != null) {
                    onResultReceived(tokenId)
                }
            } else {
                onDialogDismissed()
            }
        } catch (ex: ApiException) {
            when (ex.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onDialogDismissed()
                }

                CommonStatusCodes.NETWORK_ERROR -> {
                    onDialogDismissed()
                }

                else -> {
                    onDialogDismissed()
                }
            }
        }
    }

    LaunchedEffect(key1 = key) {
        launcher(activityLauncher)
    }
}

fun signIn(
    oneTapClient: SignInClient,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    accountNotFound: () -> Unit,
) {
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(CLIENT_ID)
                .setFilterByAuthorizedAccounts(true)
                .build(),
        )
        .setAutoSelectEnabled(true)
        .build()

    oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender,
                    ).build(),
                )
            } catch (ex: Exception) {
                Log.d("SignIn", "Couldn't start One Tap UI: ${ex.message}")
            }
        }
        .addOnFailureListener {
            signUp(
                oneTapClient = oneTapClient,
                launchActivityResult = launchActivityResult,
                accountNotFound = accountNotFound,
            )
        }
}

fun signUp(
    oneTapClient: SignInClient,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    accountNotFound: () -> Unit,
) {
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build(),
        )
        .build()

    oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender,
                    ).build(),
                )
            } catch (ex: Exception) {
                Log.d("SignUp", "Couldn't start One Tap UI: ${ex.message}")
            }
        }
        .addOnFailureListener {
            accountNotFound()
        }
}
