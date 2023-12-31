package com.islaharper.dawnofandroid.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islaharper.dawnofandroid.domain.useCases.readOnboarding.ReadOnBoardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val readOnBoardingStateUseCase: ReadOnBoardingStateUseCase) :
    ViewModel() {
    private val _onBoardingComplete = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingComplete

    init {
        viewModelScope.launch {
            _onBoardingComplete.value =
                readOnBoardingStateUseCase.invoke().stateIn(viewModelScope).value
        }
    }
}
