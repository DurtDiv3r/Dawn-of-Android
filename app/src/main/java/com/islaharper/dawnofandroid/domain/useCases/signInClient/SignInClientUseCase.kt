package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.islaharper.dawnofandroid.util.Resource

interface SignInClientUseCase {
    suspend operator fun invoke(): Resource<BeginSignInResult>
}
