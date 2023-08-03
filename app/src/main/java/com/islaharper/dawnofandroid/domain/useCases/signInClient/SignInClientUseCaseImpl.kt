package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.islaharper.dawnofandroid.domain.repository.AuthRepository
import com.islaharper.dawnofandroid.util.Resource
import javax.inject.Inject

class SignInClientUseCaseImpl @Inject constructor(private val repository: AuthRepository) :
    SignInClientUseCase {
    override suspend operator fun invoke(): Resource<BeginSignInResult> {
        return repository.oneTapSignIn()
    }
}
