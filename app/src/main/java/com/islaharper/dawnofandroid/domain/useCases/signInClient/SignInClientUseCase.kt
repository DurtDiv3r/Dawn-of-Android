package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface SignInClientUseCase {
    suspend operator fun invoke(): Flow<Resource<BeginSignInResult>>
}
