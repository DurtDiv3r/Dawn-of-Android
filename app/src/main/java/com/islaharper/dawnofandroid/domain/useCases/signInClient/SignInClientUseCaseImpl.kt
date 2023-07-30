package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.islaharper.dawnofandroid.data.repository.OneTapSignInResponse
import com.islaharper.dawnofandroid.domain.repository.AuthRepository
import javax.inject.Inject

class SignInClientUseCaseImpl @Inject constructor(private val repository: AuthRepository) :
    SignInClientUseCase {
    override suspend operator fun invoke(): OneTapSignInResponse {
        return repository.oneTapSignIn()
    }
}
