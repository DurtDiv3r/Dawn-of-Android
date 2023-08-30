package com.islaharper.dawnofandroid.data.repository

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.domain.repository.AuthRepository
import com.islaharper.dawnofandroid.util.Constants
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private var oneTapClient: SignInClient
) : AuthRepository {

    override suspend fun oneTapSignIn(): Resource<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(
                BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(Constants.CLIENT_ID)
                            .setFilterByAuthorizedAccounts(true)
                            .build(),
                    )
                    .setAutoSelectEnabled(true)
                    .build()
            ).await()
            Resource.Success(signInResult)
        } catch (ex: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(
                    BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(Constants.CLIENT_ID)
                                .setFilterByAuthorizedAccounts(false)
                                .build(),
                        )
                        .build(),
                ).await()
                Resource.Success(signUpResult)
            } catch (ex: Exception) {
                Resource.Error(ex)
            }
        }
    }
}
