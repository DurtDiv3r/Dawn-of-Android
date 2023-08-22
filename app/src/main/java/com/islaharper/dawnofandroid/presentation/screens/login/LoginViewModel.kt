package com.islaharper.dawnofandroid.presentation.screens.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCase
import com.islaharper.dawnofandroid.util.Constants
import com.islaharper.dawnofandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oneTapClient: SignInClient,
    private val readSignedInStateUseCase: ReadSignedInStateUseCase,
    private val saveSignedInStateUseCase: SaveSignedInStateUseCase,
    private val verifyTokenUseCase: VerifyTokenUseCase,
) : ViewModel() {

    private val _launchOneTapSignIn = MutableStateFlow(false)
    val launchOneTapSignIn: StateFlow<Boolean> = _launchOneTapSignIn

    private val _messageBarState = MutableStateFlow<MessageBarState>(MessageBarState.Idle)
    val messageBarState: StateFlow<MessageBarState> = _messageBarState

    private val _navigationState = MutableStateFlow(false)
    val navigationState: StateFlow<Boolean> = _navigationState

    private val _oneTapSignInResponse = MutableStateFlow<Resource<BeginSignInResult>>(Resource.Idle)
    val oneTapSignInResponse: StateFlow<Resource<BeginSignInResult>> = _oneTapSignInResponse

    init {
        viewModelScope.launch {
            _launchOneTapSignIn.value = readSignedInStateUseCase().stateIn(viewModelScope).value
        }
    }

    fun onOneTapSignInSuccess(result: Intent?) {
        val credentials = oneTapClient.getSignInCredentialFromIntent(result)
        val tokenId = credentials.googleIdToken

        if (tokenId != null) {
            viewModelScope.launch {
                verifyTokenUseCase.invoke(request = ApiTokenRequest(tokenId = tokenId))
                    .collect { response ->
                        when (response) {
                            is Resource.Idle -> {
                            }

                            is Resource.Success -> {
                                _messageBarState.value = MessageBarState.Success(
                                    message = "Successfully Signed In",
                                )
                                _navigationState.value = response.data.success
                                saveLaunchSignInState(launchedSignIn = false)
                            }

                            is Resource.Error -> {
                                val failureMessage = when (response.ex) {
                                    is SocketTimeoutException -> {
                                        "Connection Timeout Exception"
                                    }

                                    is ConnectException -> {
                                        "Internet Connection Unavailable"
                                    }

                                    is IOException -> {
                                        "Server Unreachable"
                                    }

                                    else -> {
                                        response.ex?.message ?: "Unknown Error"
                                    }
                                }
                                onOneTapSignInFailure(failureMessage = failureMessage)
                            }
                        }
                    }
            }
        }
    }

    fun onOneTapSignInFailure(failureMessage: String = "") {
        if (failureMessage.isNotBlank()) {
            _messageBarState.value = MessageBarState.Failure(message = failureMessage)
        }
        saveLaunchSignInState(launchedSignIn = false)
        _navigationState.value = false
    }

    fun onGoogleButtonClick() = viewModelScope.launch {
        saveLaunchSignInState(true)

        _oneTapSignInResponse.value = try {
            val signInResult = oneTapClient
                .beginSignIn(
                    BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(Constants.CLIENT_ID)
                                .setFilterByAuthorizedAccounts(true)
                                .build(),
                        )
                        .setAutoSelectEnabled(true)
                        .build(),
                )
                .await()
            Resource.Success(signInResult)
        } catch (ex: Exception) {
            try {
                val signUpResult = oneTapClient
                    .beginSignIn(
                        BeginSignInRequest.builder()
                            .setGoogleIdTokenRequestOptions(
                                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                    .setSupported(true)
                                    .setServerClientId(Constants.CLIENT_ID)
                                    .setFilterByAuthorizedAccounts(false)
                                    .build(),
                            )
                            .build(),
                    )
                    .await()
                Resource.Success(signUpResult)
            } catch (ex: Exception) {
                onOneTapSignInFailure(failureMessage = ex.message ?: "Error Signing In")
                Resource.Error(ex)
            }
        }
    }

    fun saveLaunchSignInState(launchedSignIn: Boolean) {
        viewModelScope.launch {
            _launchOneTapSignIn.value =
                saveSignedInStateUseCase(launchedSignIn).stateIn(viewModelScope).value
        }
    }

    fun onNavigateToHomeScreen() {
        _navigationState.value = false
    }
}
