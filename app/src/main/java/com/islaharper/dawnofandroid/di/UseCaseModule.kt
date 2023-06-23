package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.useCases.readDarkTheme.ReadDarkModeUseCase
import com.islaharper.dawnofandroid.domain.useCases.readDynamicTheme.ReadDynamicThemeUseCase
import com.islaharper.dawnofandroid.domain.useCases.readOnboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme.SaveDarkModeUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme.SaveDynamicThemeUseCase
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
    fun provideSaveDarkModeUseCase(repository: Repository): SaveDarkModeUseCase {
        return SaveDarkModeUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideReadDarkModeUseCase(repository: Repository): ReadDarkModeUseCase {
        return ReadDarkModeUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideSaveDynamicThemeUseCase(repository: Repository): SaveDynamicThemeUseCase {
        return SaveDynamicThemeUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideReadDynamicThemeUseCase(repository: Repository): ReadDynamicThemeUseCase {
        return ReadDynamicThemeUseCase(repository = repository)
    }

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
