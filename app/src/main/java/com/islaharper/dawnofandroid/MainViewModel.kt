package com.islaharper.dawnofandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islaharper.dawnofandroid.domain.useCases.readDarkTheme.ReadDarkModeUseCase
import com.islaharper.dawnofandroid.domain.useCases.readDynamicTheme.ReadDynamicThemeUseCase
import com.islaharper.dawnofandroid.domain.useCases.readOnboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme.SaveDarkModeUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme.SaveDynamicThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readOnBoardingStateUseCase: ReadOnBoardingStateUseCase,
    private val readDarkModeUseCase: ReadDarkModeUseCase,
    private val saveDarkModeUseCase: SaveDarkModeUseCase,
    private val readDynamicThemeUseCase: ReadDynamicThemeUseCase,
    private val saveDynamicThemeUseCase: SaveDynamicThemeUseCase,
) : ViewModel() {
    private val _darkModeState: MutableState<Boolean> = mutableStateOf(false)
    val darkModeState: State<Boolean> = _darkModeState

    private val _dynamicThemeState: MutableState<Boolean> = mutableStateOf(false)
    val dynamicThemeState: State<Boolean> = _dynamicThemeState

    private val _onBoardingState: MutableState<Boolean> = mutableStateOf(false)
    val onBoardingState: State<Boolean> = _onBoardingState

    init {
        viewModelScope.launch {
            _onBoardingState.value =
                readOnBoardingStateUseCase.invoke().stateIn(viewModelScope).value
            _darkModeState.value =
                readDarkModeUseCase.invoke().stateIn(viewModelScope).value
            _dynamicThemeState.value =
                readDynamicThemeUseCase.invoke().stateIn(viewModelScope).value
        }
    }

    fun saveDarkModeState(isDarkMode: Boolean) {
        viewModelScope.launch {
            saveDarkModeUseCase(isDarkMode = isDarkMode)
        }
    }

    fun saveDynamicThemeState(isDynamicTheme: Boolean) {
        viewModelScope.launch {
            saveDynamicThemeUseCase(isDynamicTheme = isDynamicTheme)
        }
    }
}
