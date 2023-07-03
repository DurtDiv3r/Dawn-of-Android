package com.islaharper.dawnofandroid.presentation.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val readSignedInStateUseCase: ReadSignedInStateUseCase,
    private val saveSignedInStateUseCase: SaveSignedInStateUseCase,
    private val verifyTokenUseCase: VerifyTokenUseCase,
) : ViewModel() {
    private val _signedInState: MutableState<Boolean> = mutableStateOf(false)
    val signedInState: State<Boolean> = _signedInState

    private val _messageBarState: MutableState<MessageBarState> =
        mutableStateOf(MessageBarState.Idle)
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _apiResponse: MutableState<Resource<ApiResponse>> = mutableStateOf(Resource.Idle)
    val apiResponse: State<Resource<ApiResponse>> = _apiResponse

    init {
        viewModelScope.launch {
            readSignedInStateUseCase.invoke().collect { isSignedIn ->
                _signedInState.value = isSignedIn
            }
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch {
            saveSignedInStateUseCase(signedIn)
        }
    }

    fun updateMessageBarErrorState(errorMessage: String) {
        _messageBarState.value = MessageBarState.Failure(message = errorMessage)
    }

    fun verifyTokenOnBackend(apiRequest: ApiTokenRequest) {
        _apiResponse.value = Resource.Loading
        try {
            viewModelScope.launch {
                val response = verifyTokenUseCase(request = apiRequest)
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

                            else -> {
                                response.ex?.message.toString()
                            }
                        },
                    )
                }
            }
        } catch (ex: Exception) {
            _apiResponse.value = Resource.Error(ex = ex)
            _messageBarState.value = MessageBarState.Failure(message = ex.message.toString())
        }
    }
}
