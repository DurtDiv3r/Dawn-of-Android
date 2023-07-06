package com.islaharper.dawnofandroid.domain.useCases.saveSignedInState

import kotlinx.coroutines.flow.Flow

interface SaveSignedInStateUseCase {
    suspend operator fun invoke(signedIn: Boolean): Flow<Boolean>
}
