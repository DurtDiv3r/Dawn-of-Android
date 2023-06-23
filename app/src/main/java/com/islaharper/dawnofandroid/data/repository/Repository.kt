package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val dataStoreOperations: DataStoreOperations) {

    // Dark Mode State
    suspend fun saveDarkModeState(isDarkMode: Boolean) {
        dataStoreOperations.saveDarkModeState(isDarkMode = isDarkMode)
    }

    fun readDarkModeState(): Flow<Boolean> {
        return dataStoreOperations.readDarkModeState()
    }

    // Dynamic Theme State
    suspend fun saveDynamicThemeState(isDynamicTheme: Boolean) {
        dataStoreOperations.saveDynamicThemeState(isDynamicTheme = isDynamicTheme)
    }

    fun readDynamicThemeState(): Flow<Boolean> {
        return dataStoreOperations.readDynamicThemeState()
    }

    // OnBoarding State
    suspend fun saveOnBoardingState(isCompleted: Boolean) {
        dataStoreOperations.saveOnBoardingState(isComplete = isCompleted)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperations.readOnBoardingState()
    }
}
