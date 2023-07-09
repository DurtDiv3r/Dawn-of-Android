package com.islaharper.dawnofandroid.domain.useCases.readSignedInState

import kotlinx.coroutines.flow.Flow

interface ReadSignedInStateUseCase {
    operator fun invoke(): Flow<Boolean>
}
