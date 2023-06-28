package com.islaharper.dawnofandroid.domain.useCases.readOnboarding

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadOnBoardingStateUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}
