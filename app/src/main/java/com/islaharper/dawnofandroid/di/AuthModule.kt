package com.islaharper.dawnofandroid.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.islaharper.dawnofandroid.data.repository.AuthRepositoryImpl
import com.islaharper.dawnofandroid.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideOneTapClient(@ApplicationContext context: Context) =
        Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideAuthRepository(oneTapClient: SignInClient): AuthRepository =
        AuthRepositoryImpl(oneTapClient = oneTapClient)
}
