package com.islaharper.dawnofandroid.domain.useCases

import com.islaharper.dawnofandroid.domain.useCases.readOnboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveOnboarding.SaveOnBoardingStateUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingStateUseCase,
    val readOnBoardingUseCase: ReadOnBoardingStateUseCase,
)
