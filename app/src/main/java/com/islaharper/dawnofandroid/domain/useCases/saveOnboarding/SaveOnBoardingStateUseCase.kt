package com.islaharper.dawnofandroid.domain.useCases.saveOnboarding

import com.islaharper.dawnofandroid.data.repository.Repository

class SaveOnBoardingStateUseCase(private val repository: Repository) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed)
    }
}
