package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.repository.DataStoreOperations
import com.islaharper.dawnofandroid.domain.repository.RemoteDataSource
import com.islaharper.dawnofandroid.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource
) {

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

    // Verify token for access to authorised endpoints
    suspend fun verifyToken(request: ApiTokenRequest): Resource<ApiResponse> {
        return remoteDataSource.verifyToken(request)
    }
}
