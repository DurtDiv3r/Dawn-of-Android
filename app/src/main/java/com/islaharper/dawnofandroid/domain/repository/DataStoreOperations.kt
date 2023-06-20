package com.islaharper.dawnofandroid.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    // For onboarding status for displaying Welcome Screen
    suspend fun saveOnBoardingState(isComplete: Boolean)
    fun readOnBoardingState(): Flow<Boolean>

    // For sign in button state
    suspend fun saveSignInState(signedIn: Boolean)
    fun readSignInState(): Flow<Boolean>
}
