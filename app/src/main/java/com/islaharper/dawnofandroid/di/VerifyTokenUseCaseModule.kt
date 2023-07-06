package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class VerifyTokenUseCaseModule {

    @Binds
    abstract fun provideVerifyTokenUseCase(useCase: VerifyTokenUseCaseImpl): VerifyTokenUseCase
}
