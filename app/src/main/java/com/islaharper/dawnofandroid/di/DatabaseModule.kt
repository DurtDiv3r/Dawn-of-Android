package com.islaharper.dawnofandroid.di

import android.content.Context
import androidx.room.Room
import com.islaharper.dawnofandroid.data.local.AndroidFlavourDb
import com.islaharper.dawnofandroid.util.Constants
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
        return Room.databaseBuilder(
            context,
            AndroidFlavourDb::class.java,
            Constants.FLAVOUR_DB,
        ).build()
    }
}
