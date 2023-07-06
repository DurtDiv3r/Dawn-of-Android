package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.SaveSignedInStateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SaveSignedInStateUseCaseModule {

    @Binds
    abstract fun provideSaveSignedInStateUseCase(useCase: SaveSignedInStateUseCaseImpl): SaveSignedInStateUseCase
}
