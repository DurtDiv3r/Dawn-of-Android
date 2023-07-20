package com.islaharper.dawnofandroid.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.islaharper.dawnofandroid.data.local.AndroidFlavourDb
import com.islaharper.dawnofandroid.data.pagingSource.FlavourRemoteMediator
import com.islaharper.dawnofandroid.data.pagingSource.SearchFlavoursSource
import com.islaharper.dawnofandroid.data.remote.MyApi
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.domain.repository.RemoteDataSource
import com.islaharper.dawnofandroid.util.Constants.ITEMS_PER_PAGE
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val myApi: MyApi,
    private val androidFlavourDb: AndroidFlavourDb,
) : RemoteDataSource {

    private val androidFlavourDao = androidFlavourDb.androidFlavourDao()

    override suspend fun verifyToken(request: ApiTokenRequest): Resource<ApiResponse> {
        return try {
            val response = myApi.verifyToken(request = request)
            Resource.Success(data = response)
        } catch (ex: Exception) {
            Resource.Error(ex = ex)
        }
    }

    override suspend fun clearSession(): Resource<ApiResponse> {
        return try {
            val response = myApi.clearSession()
            Resource.Success(data = response)
        } catch (ex: Exception) {
            Resource.Error(ex = ex)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllFlavours(): Flow<PagingData<Flavour>> {
        val pagingSourceFactory = {
            androidFlavourDao.getAllFlavours()
        }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = FlavourRemoteMediator(myApi, androidFlavourDb),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun searchFlavours(query: String): Flow<PagingData<Flavour>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchFlavoursSource(myApi, query)
            },
        ).flow
    }
}
