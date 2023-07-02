package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.repository.DataStoreOperations
import com.islaharper.dawnofandroid.domain.repository.RemoteDataSource
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource,
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

    // Signed In State
    suspend fun saveSignedInState(signedIn: Boolean) {
        dataStoreOperations.saveSignInState(signedIn)
    }

    fun readSignedInState(): Flow<Boolean> {
        return dataStoreOperations.readSignInState()
    }

    // Verify token for access to authorised endpoints
    suspend fun verifyTokenOnBackend(request: ApiTokenRequest): Resource<ApiResponse> {
        return remoteDataSource.verifyTokenOnBackend(request)
    }
}
