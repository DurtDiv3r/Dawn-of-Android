package com.islaharper.dawnofandroid.domain.useCases.signInClient

import android.content.Context
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.data.repository.Repository
import javax.inject.Inject

class SignInClientUseCaseImpl @Inject constructor(private val repository: Repository) :
    SignInClientUseCase {
    override operator fun invoke(context: Context): SignInClient {
        return repository.getSignInClient(context = context)
    }
}
