package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReadSignedInStateUseCaseModule {

    @Binds
    abstract fun provideReadSignedInStateUseCase(useCase: ReadSignedInStateUseCaseImpl): ReadSignedInStateUseCase
}
