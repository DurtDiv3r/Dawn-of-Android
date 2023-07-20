package com.islaharper.dawnofandroid.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.islaharper.dawnofandroid.data.remote.MyApi
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.domain.model.ResponseData
import com.islaharper.dawnofandroid.util.Resource

class SearchFlavoursSource(private val api: MyApi, private val query: String) :
    PagingSource<Int, Flavour>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Flavour> {
        return try {
            val response = getFlavours(query = query)
            if (response is Resource.Success) {
                val flavours = response.data.flavours
                if (flavours?.isNotEmpty() == true) {
                    LoadResult.Page(
                        data = flavours,
                        prevKey = response.data.prevPage,
                        nextKey = response.data.nextPage,
                    )
                } else {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null,
                    )
                }
            } else if (response is Resource.Error) {
                LoadResult.Error(response.ex ?: Exception())
            } else {
                LoadResult.Error(Exception())
            }
        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Flavour>): Int? {
        return state.anchorPosition
    }

    private suspend fun getFlavours(query: String): Resource<ResponseData> {
        return try {
            val response = api.searchForFlavour(query = query)
            Resource.Success(data = response.responseData!!)
        } catch (ex: Exception) {
            Resource.Error(ex = ex)
        }
    }
}
