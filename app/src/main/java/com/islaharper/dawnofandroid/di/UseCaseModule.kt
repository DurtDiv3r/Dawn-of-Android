package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCaseImpl
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCaseImpl
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.VerifyTokenUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun provideReadSignedInStateUseCase(useCase: ReadSignedInStateUseCaseImpl): ReadSignedInStateUseCase

    @Binds
    abstract fun provideSaveSignedInStateUseCase(useCase: SaveSignedInStateUseCaseImpl): SaveSignedInStateUseCase

    @Binds
    abstract fun provideVerifyTokenUseCase(useCase: VerifyTokenUseCaseImpl): VerifyTokenUseCase
}
