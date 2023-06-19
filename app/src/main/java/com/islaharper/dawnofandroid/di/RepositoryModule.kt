package com.islaharper.dawnofandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.islaharper.dawnofandroid.data.repository.DataStoreOperationsImpl
import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.repository.DataStoreOperations
import com.islaharper.dawnofandroid.domain.use_cases.UseCases
import com.islaharper.dawnofandroid.domain.use_cases.read_onboarding.ReadOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.read_signed_in_state.ReadSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.save_onboarding.SaveOnBoardingStateUseCase
import com.islaharper.dawnofandroid.domain.use_cases.save_signed_in_state.SaveSignedInStateUseCase
import com.islaharper.dawnofandroid.util.Constants.PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(PREFS_NAME)
            },
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(dataStore: DataStore<Preferences>): DataStoreOperations {
        return DataStoreOperationsImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingStateUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingStateUseCase(repository),
            saveSignedInStateUseCase = SaveSignedInStateUseCase(repository),
            readSignedInStateUseCase = ReadSignedInStateUseCase(repository),
        )
    }
}
