package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.data.remote.MyApi
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.repository.RemoteDataSource
import com.islaharper.dawnofandroid.util.Resource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val myApi: MyApi) : RemoteDataSource {

    override suspend fun verifyToken(request: ApiTokenRequest): Resource<ApiResponse> {
        return try {
            val response = myApi.verifyToken(request = request)
            Resource.Success(data = response)
        } catch (ex: Exception) {
            Resource.Error(ex = ex)
        }
    }
}
