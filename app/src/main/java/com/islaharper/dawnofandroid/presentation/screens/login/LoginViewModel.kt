package com.islaharper.dawnofandroid.presentation.screens.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.useCases.signInClient.SignInClientUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCase
import com.islaharper.dawnofandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oneTapClient: SignInClient,
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val signInClientUseCase: SignInClientUseCase
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _launchSignIn = MutableStateFlow<BeginSignInResult?>(null)
    val launchSignIn: StateFlow<BeginSignInResult?> = _launchSignIn

    private val _messageBarState = MutableStateFlow<MessageBarState>(MessageBarState.Idle)
    val messageBarState: StateFlow<MessageBarState> = _messageBarState

    private val _navigationState = MutableStateFlow(false)
    val navigationState: StateFlow<Boolean> = _navigationState

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
                                _loading.value = false
                                _launchSignIn.value = null
                                _navigationState.value = response.data.success
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
        setToInitialSignInState()
    }

    fun onGoogleButtonClick() = viewModelScope.launch {
        _loading.value = true

        signInClientUseCase.invoke().collect { response ->
            when (response) {
                is Resource.Idle -> {}
                is Resource.Success -> {
                    _launchSignIn.value = response.data
                }

                is Resource.Error -> {
                    onOneTapSignInFailure(
                        failureMessage = response.ex?.localizedMessage ?: "Error Signing In"
                    )
                    _launchSignIn.value = null
                }
            }
        }
    }

    private fun setToInitialSignInState() {
        _loading.value = false
        _launchSignIn.value = null
        _navigationState.value = false
    }

    fun onNavigateToHomeScreen() {
        _navigationState.value = false
    }
}
