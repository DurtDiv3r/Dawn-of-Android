package com.islaharper.dawnofandroid.domain.use_cases

import com.islaharper.dawnofandroid.domain.use_cases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.read_onboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.saveSignedInState.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.save_onboarding.SaveOnBoardingStateUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingStateUseCase,
    val readOnBoardingUseCase: ReadOnBoardingStateUseCase,
    val saveSignedInStateUseCase: SaveSignedInStateUseCase,
    val readSignedInStateUseCase: ReadSignedInStateUseCase,
)
