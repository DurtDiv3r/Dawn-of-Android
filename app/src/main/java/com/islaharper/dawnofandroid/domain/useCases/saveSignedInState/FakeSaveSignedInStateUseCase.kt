package com.islaharper.dawnofandroid.domain.useCases.saveSignedInState

import kotlinx.coroutines.flow.MutableSharedFlow

class FakeSaveSignedInStateUseCase : SaveSignedInStateUseCase {
    private val fakeFlow = MutableSharedFlow<Boolean>()
    suspend fun emit(value: Boolean) = fakeFlow.emit(value)
    override suspend fun invoke(signedIn: Boolean) = fakeFlow
}
