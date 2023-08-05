package com.islaharper.dawnofandroid.presentation.screens.login

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.signInClient.SignInClientUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCase
import com.islaharper.dawnofandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oneTapClient: SignInClient,
    private val readSignedInStateUseCase: ReadSignedInStateUseCase,
    private val saveSignedInStateUseCase: SaveSignedInStateUseCase,
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val signInClientUseCase: SignInClientUseCase
) : ViewModel() {

    private val _signedInState = MutableStateFlow(false)
    val signedInState: StateFlow<Boolean> = _signedInState

    private val _messageBarState = MutableStateFlow<MessageBarState>(MessageBarState.Idle)
    val messageBarState: StateFlow<MessageBarState> = _messageBarState

    private val _apiResponse = MutableStateFlow<Resource<ApiResponse>>(Resource.Idle)
    val apiResponse: StateFlow<Resource<ApiResponse>> = _apiResponse

    var oneTapSignInResponse by mutableStateOf<Resource<BeginSignInResult>>(Resource.Idle)
        private set

    init {
        viewModelScope.launch {
            _signedInState.value = readSignedInStateUseCase().stateIn(viewModelScope).value
        }
    }

    fun oneTapSignIn() = viewModelScope.launch {
        _signedInState.value = saveSignedInStateUseCase(true).stateIn(viewModelScope).value
        oneTapSignInResponse = signInClientUseCase.invoke()
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch {
            _signedInState.value = saveSignedInStateUseCase(signedIn).stateIn(viewModelScope).value
        }
    }

    fun updateMessageBarErrorState(errorMessage: String) {
        _messageBarState.value = MessageBarState.Failure(message = errorMessage)
    }

    fun verifyToken(intent: Intent?) {
        val credentials = oneTapClient.getSignInCredentialFromIntent(intent)
        val tokenId = credentials.googleIdToken
        if (tokenId != null) {
            viewModelScope.launch {
                verifyTokenUseCase.invoke(request = ApiTokenRequest(tokenId = tokenId))
                    .collect { response ->
                        _apiResponse.value = response

                        when (response) {
                            is Resource.Idle -> {
                            }

                            is Resource.Success -> {
                                _messageBarState.value = MessageBarState.Success(
                                    message = "Successfully Signed In"
                                )
                            }

                            is Resource.Error -> {
                                _messageBarState.value = MessageBarState.Failure(
                                    message = when (response.ex) {
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
                                )
                            }
                        }
                    }
            }
        }
    }
}
