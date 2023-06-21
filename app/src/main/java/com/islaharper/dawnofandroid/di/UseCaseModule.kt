package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.useCases.readOnboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveOnboarding.SaveOnBoardingStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSaveOnBoardingUseCase(repository: Repository): SaveOnBoardingStateUseCase {
        return SaveOnBoardingStateUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideReadOnBoardingUseCase(repository: Repository): ReadOnBoardingStateUseCase {
        return ReadOnBoardingStateUseCase(repository = repository)
    }
}
