package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.data.repository.LocalDataSourceImpl
import com.islaharper.dawnofandroid.domain.repository.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}
