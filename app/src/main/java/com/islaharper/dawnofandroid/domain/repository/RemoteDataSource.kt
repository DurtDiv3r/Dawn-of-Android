package com.islaharper.dawnofandroid.domain.repository

import androidx.paging.PagingData
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    // Backend: Verify token for access to authorised endpoints
    suspend fun verifyToken(request: ApiTokenRequest): Resource<ApiResponse>
    suspend fun clearSession(): Resource<ApiResponse>

    // Get Android Flavours
    fun getAllFlavours(): Flow<PagingData<Flavour>>
    fun searchFlavours(query: String): Flow<PagingData<Flavour>>
}
