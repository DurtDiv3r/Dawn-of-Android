package com.islaharper.dawnofandroid.domain.useCases.signInClient

import android.content.Context
import com.google.android.gms.auth.api.identity.SignInClient

interface SignInClientUseCase {
    operator fun invoke(context: Context): SignInClient
}
