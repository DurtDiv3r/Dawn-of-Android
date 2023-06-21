package com.islaharper.dawnofandroid.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islaharper.dawnofandroid.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
    private val _onBoardingComplete = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingComplete

    init {
        viewModelScope.launch {
            _onBoardingComplete.value =
                useCases.readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }
}
