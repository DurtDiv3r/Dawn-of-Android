package com.islaharper.dawnofandroid.di

import com.islaharper.dawnofandroid.data.repository.RemoteDataSourceImpl
import com.islaharper.dawnofandroid.domain.repository.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}
