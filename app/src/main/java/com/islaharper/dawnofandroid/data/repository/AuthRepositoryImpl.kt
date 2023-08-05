package com.islaharper.dawnofandroid.data.repository

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.domain.repository.AuthRepository
import com.islaharper.dawnofandroid.util.Constants.SIGN_IN_REQUEST
import com.islaharper.dawnofandroid.util.Constants.SIGN_UP_REQUEST
import com.islaharper.dawnofandroid.util.Resource
import javax.inject.Named
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest
) : AuthRepository {

    override suspend fun oneTapSignIn(): Resource<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Resource.Success(signInResult)
        } catch (ex: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Resource.Success(signUpResult)
            } catch (ex: Exception) {
                Resource.Error(ex)
            }
        }
    }
}
