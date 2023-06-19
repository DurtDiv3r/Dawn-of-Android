package com.islaharper.dawnofandroid.domain.repository

import com.islaharper.dawnofandroid.domain.model.ApiRequest
import com.islaharper.dawnofandroid.domain.model.ApiResponse

interface RemoteDataSource {
    // Backend: Verify token for access to authorised endpoints
    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse
}
