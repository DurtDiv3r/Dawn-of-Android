package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSignInClientUseCase : SignInClientUseCase {
    private val fakeFlow = MutableStateFlow<Resource<BeginSignInResult>>(Resource.Idle)

    suspend fun emit(value: Resource<BeginSignInResult>) = fakeFlow.emit(value)

    override suspend fun invoke() = fakeFlow
}
