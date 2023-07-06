package com.islaharper.dawnofandroid.domain.useCases.verifyToken

import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface VerifyTokenUseCase {
    suspend operator fun invoke(request: ApiTokenRequest): Flow<Resource<ApiResponse>>
}
