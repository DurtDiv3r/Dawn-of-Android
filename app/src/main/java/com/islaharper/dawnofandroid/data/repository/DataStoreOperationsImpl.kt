package com.islaharper.dawnofandroid.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.islaharper.dawnofandroid.domain.repository.DataStoreOperations
import com.islaharper.dawnofandroid.util.Constants.PREFS_ONBOARDING_KEY
import com.islaharper.dawnofandroid.util.Constants.PREFS_SIGNED_IN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreOperations {

    private object PrefsOnBoardingKey {
        val onBoardingKey = booleanPreferencesKey(name = PREFS_ONBOARDING_KEY)
    }

    override suspend fun saveOnBoardingState(isComplete: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsOnBoardingKey.onBoardingKey] = isComplete
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { ex ->
                if (ex is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw ex
                }
            }.map { preferences ->
                val onBoardingState = preferences[PrefsOnBoardingKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    private object PrefsSignedInKey {
        val signedInKey = booleanPreferencesKey(name = PREFS_SIGNED_IN_KEY)
    }

    override suspend fun saveSignInState(signedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsSignedInKey.signedInKey] = signedIn
        }
    }

    override fun readSignInState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val signedInState = preferences[PrefsSignedInKey.signedInKey] ?: false
                signedInState
            }
    }
}
