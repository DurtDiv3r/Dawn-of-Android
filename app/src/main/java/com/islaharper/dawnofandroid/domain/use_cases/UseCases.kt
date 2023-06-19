package com.islaharper.dawnofandroid.domain.use_cases

import com.islaharper.dawnofandroid.domain.use_cases.read_onboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.read_signed_in_state.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.save_onboarding.SaveOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.save_signed_in_state.SaveSignedInStateUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingStateUseCase,
    val readOnBoardingUseCase: ReadOnBoardingStateUseCase,
    val saveSignedInStateUseCase: SaveSignedInStateUseCase,
    val readSignedInStateUseCase: ReadSignedInStateUseCase,
)
