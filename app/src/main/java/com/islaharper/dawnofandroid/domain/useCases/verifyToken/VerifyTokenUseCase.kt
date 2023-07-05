package com.islaharper.dawnofandroid.domain.useCases.verifyToken

import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.util.Resource
import javax.inject.Inject

class VerifyTokenUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(request: ApiTokenRequest): Resource<ApiResponse> {
        return repository.verifyToken(request = request)
    }
}
