package com.islaharper.dawnofandroid.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    // For Dark mode state
    suspend fun saveDarkModeState(isDarkMode: Boolean)
    fun readDarkModeState(): Flow<Boolean>

    // For Dynamic Theme state
    suspend fun saveDynamicThemeState(isDynamicTheme: Boolean)
    fun readDynamicThemeState(): Flow<Boolean>

    // For onboarding status for displaying Welcome Screen
    suspend fun saveOnBoardingState(isComplete: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}
