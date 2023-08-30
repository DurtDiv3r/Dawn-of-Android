package com.islaharper.dawnofandroid.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.islaharper.dawnofandroid.util.Resource

interface AuthRepository {
    suspend fun oneTapSignIn(): Resource<BeginSignInResult>
}
