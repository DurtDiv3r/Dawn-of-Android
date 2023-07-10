package com.islaharper.dawnofandroid.domain.useCases.readSignedInState

import kotlinx.coroutines.flow.MutableSharedFlow

class FakeReadSignInStateUseCase : ReadSignedInStateUseCase {

    private val fakeFlow = MutableSharedFlow<Boolean>()
    suspend fun emit(value: Boolean) = fakeFlow.emit(value)
    override fun invoke() = fakeFlow
}
