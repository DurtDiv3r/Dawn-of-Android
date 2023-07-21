package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.domain.useCases.getAllFlavours.GetAllFlavoursUseCase
import com.islaharper.dawnofandroid.domain.useCases.getAllFlavours.GetAllFlavoursUseCaseImpl
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.ReadSignedInStateUseCaseImpl
import com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme.SaveDarkModeUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme.SaveDarkModeUseCaseImpl
import com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme.SaveDynamicThemeUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme.SaveDynamicThemeUseCaseImpl
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
    abstract fun provideSaveDarkModeUseCase(useCase: SaveDarkModeUseCaseImpl): SaveDarkModeUseCase

    @Binds
    abstract fun provideSaveDynamicThemeUseCase(useCase: SaveDynamicThemeUseCaseImpl): SaveDynamicThemeUseCase

    @Binds
    abstract fun provideReadSignedInStateUseCase(useCase: ReadSignedInStateUseCaseImpl): ReadSignedInStateUseCase

    @Binds
    abstract fun provideSaveSignedInStateUseCase(useCase: SaveSignedInStateUseCaseImpl): SaveSignedInStateUseCase

    @Binds
    abstract fun provideVerifyTokenUseCase(useCase: VerifyTokenUseCaseImpl): VerifyTokenUseCase

    @Binds
    abstract fun provideGetAllFlavoursUseCase(usecase: GetAllFlavoursUseCaseImpl): GetAllFlavoursUseCase
}
