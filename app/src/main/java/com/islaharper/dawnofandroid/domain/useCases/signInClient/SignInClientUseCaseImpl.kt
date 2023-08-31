package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.islaharper.dawnofandroid.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInClientUseCaseImpl @Inject constructor(private val repository: AuthRepository) :
    SignInClientUseCase {
    override suspend operator fun invoke() = flow {
        emit(repository.oneTapSignIn())
    }
}
