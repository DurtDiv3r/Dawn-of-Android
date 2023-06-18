package com.islaharper.dawnofandroid.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    // For onboarding status for displaying Welcome Screen
    suspend fun saveOnBoardingState(isComplete: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}
