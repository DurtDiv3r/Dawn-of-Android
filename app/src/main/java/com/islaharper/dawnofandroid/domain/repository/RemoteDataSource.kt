package com.islaharper.dawnofandroid.domain.repository

import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.util.Resource

interface RemoteDataSource {
    // Backend: Verify token for access to authorised endpoints
    suspend fun verifyToken(request: ApiTokenRequest): Resource<ApiResponse>
}
