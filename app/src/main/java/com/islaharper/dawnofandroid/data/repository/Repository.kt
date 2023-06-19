package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val dataStoreOperations: DataStoreOperations) {

    // OnBoarding State
    suspend fun saveOnBoardingState(isCompleted: Boolean) {
        dataStoreOperations.saveOnBoardingState(isCompleted)
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
}
