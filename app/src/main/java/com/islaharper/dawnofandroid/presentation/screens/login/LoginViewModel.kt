package com.islaharper.dawnofandroid.presentation.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islaharper.dawnofandroid.domain.model.ApiRequest
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.use_cases.UseCases
import com.islaharper.dawnofandroid.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
    private val _signedInState: MutableState<Boolean> = mutableStateOf(false)
    val signedInState: State<Boolean> = _signedInState

    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    init {
        viewModelScope.launch {
            useCases.readSignedInStateUseCase.invoke().collect { isSignedIn ->
                _signedInState.value = isSignedIn
            }
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.saveSignedInStateUseCase(signedIn)
        }
    }

    fun updateMessageBarState() {
        _messageBarState.value = MessageBarState(error = AccountNotFoundException())
    }

    fun verifyTokenOnBackend(apiRequest: ApiRequest) {
        _apiResponse.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = useCases.verifyTokenUseCase(request = apiRequest)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error,
                )
            }
        } catch (ex: Exception) {
            _apiResponse.value = RequestState.Error(ex)
            _messageBarState.value = MessageBarState(error = ex)
        }
    }
}

class AccountNotFoundException(override val message: String? = "Google Account not found") :
    Exception()
