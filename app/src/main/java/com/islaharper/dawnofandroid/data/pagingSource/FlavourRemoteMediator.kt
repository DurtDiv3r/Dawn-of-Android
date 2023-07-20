package com.islaharper.dawnofandroid.data.pagingSource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.islaharper.dawnofandroid.data.local.AndroidFlavourDb
import com.islaharper.dawnofandroid.data.remote.MyApi
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.domain.model.RemoteKeys
import com.islaharper.dawnofandroid.domain.model.ResponseData
import com.islaharper.dawnofandroid.util.Resource

@OptIn(ExperimentalPagingApi::class)
class FlavourRemoteMediator(
    private val api: MyApi,
    private val flavourDb: AndroidFlavourDb,
) : RemoteMediator<Int, Flavour>() {

    private val flavourDao = flavourDb.androidFlavourDao()
    private val remoteKeysDao = flavourDb.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        var lastUpdated = 0L
        try {
            remoteKeysDao.getRemoteKeys(id = 1).lastUpdate?.let {
                lastUpdated = it
            }
        } catch (e: Exception) {
            Log.d("RemoteMediator", "Exception : {${e.message}")
        }
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Flavour>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null,
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null,
                        )
                    nextPage
                }
            }

            when (val response = getFlavours(page = page)) {
                is Resource.Success -> {
                    if (response.data.flavours?.isNotEmpty() == true) {
                        flavourDb.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                flavourDao.deleteAll()
                                remoteKeysDao.deleteAllRemoteKeys()
                            }
                            val prevPage = response.data.prevPage
                            val nextPage = response.data.nextPage
                            val keys = response.data.flavours.map { flavour ->
                                RemoteKeys(
                                    id = flavour.id,
                                    prevPage = prevPage,
                                    nextPage = nextPage,
                                    lastUpdate = response.data.lastUpdated,
                                )
                            }
                            remoteKeysDao.addAllRemoteKeys(flavourRemoteKeys = keys)
                            flavourDao.addFlavours(flavours = response.data.flavours)
                        }
                    }
                    MediatorResult.Success(endOfPaginationReached = response.data.nextPage == null)
                }

                is Resource.Error -> {
                    MediatorResult.Error(response.ex ?: Exception())
                }

                else -> {
                    MediatorResult.Error(Exception())
                }
            }
        } catch (ex: Exception) {
            return MediatorResult.Error(ex)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Flavour>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Flavour>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { book ->
            remoteKeysDao.getRemoteKeys(id = book.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Flavour>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { book ->
            remoteKeysDao.getRemoteKeys(id = book.id)
        }
    }

    private suspend fun getFlavours(page: Int): Resource<ResponseData> {
        return try {
            val response = api.getAllFlavours(page = page)
            Resource.Success(data = response.responseData!!)
        } catch (ex: Exception) {
            Resource.Error(ex = ex)
        }
    }
}
