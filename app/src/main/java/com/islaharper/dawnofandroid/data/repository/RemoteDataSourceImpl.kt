package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.data.remote.MyApi
import com.islaharper.dawnofandroid.domain.model.ApiRequest
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.repository.RemoteDataSource

class RemoteDataSourceImpl(private val myApi: MyApi) : RemoteDataSource {

    override suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return try {
            myApi.verifyTokenOnBackend(request = request)
        } catch (ex: Exception) {
            ApiResponse(
                success = false,
                error = ex,
            )
        }
    }
}
