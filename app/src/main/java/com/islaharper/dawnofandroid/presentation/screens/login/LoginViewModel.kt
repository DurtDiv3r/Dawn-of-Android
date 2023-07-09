package com.islaharper.dawnofandroid.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCase
import com.islaharper.dawnofandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val readSignedInStateUseCase: ReadSignedInStateUseCase,
    private val saveSignedInStateUseCase: SaveSignedInStateUseCase,
    private val verifyTokenUseCase: VerifyTokenUseCase,
) : ViewModel() {

    private val _signedInState = MutableStateFlow(false)
    val signedInState: StateFlow<Boolean> = _signedInState

    private val _messageBarState = MutableStateFlow<MessageBarState>(MessageBarState.Idle)
    val messageBarState: StateFlow<MessageBarState> = _messageBarState

    private val _apiResponse = MutableStateFlow<Resource<ApiResponse>>(Resource.Idle)
    val apiResponse: StateFlow<Resource<ApiResponse>> = _apiResponse

    init {
        viewModelScope.launch {
            _signedInState.value = readSignedInStateUseCase().stateIn(viewModelScope).value
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch {
            _signedInState.value = saveSignedInStateUseCase(signedIn).stateIn(viewModelScope).value
        }
    }

    fun updateMessageBarErrorState(errorMessage: String) {
        _messageBarState.value = MessageBarState.Failure(message = errorMessage)
    }

    fun verifyToken(apiRequest: ApiTokenRequest) {
        _apiResponse.value = Resource.Loading
        viewModelScope.launch {
            verifyTokenUseCase.invoke(request = apiRequest).collect { response ->
                _apiResponse.value = response
                if (response is Resource.Success) {
                    _messageBarState.value = MessageBarState.Success(
                        message = "Successfully Signed In",
                    )
                } else if (response is Resource.Error) {
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
                                response.ex?.message.toString()
                            }
                        },
                    )
                }
            }
        }
    }
}
