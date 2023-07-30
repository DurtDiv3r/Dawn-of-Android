package com.islaharper.dawnofandroid.domain.useCases.signInClient

import com.islaharper.dawnofandroid.data.repository.OneTapSignInResponse

interface SignInClientUseCase {
    suspend operator fun invoke(): OneTapSignInResponse
}
