package com.islaharper.dawnofandroid.domain.use_cases.read_onboarding

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingStateUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}
