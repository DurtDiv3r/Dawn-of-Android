package com.islaharper.dawnofandroid.di

import android.content.Context
import com.islaharper.dawnofandroid.data.local.AndroidFlavourDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AndroidFlavourDb {
        return AndroidFlavourDb.create(
            context = context,
            useInMemory = false
        )
    }
}
