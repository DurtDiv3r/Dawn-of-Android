package com.islaharper.dawnofandroid.domain.use_cases.verify_token

import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.model.ApiRequest
import com.islaharper.dawnofandroid.domain.model.ApiResponse

class VerifyTokenUseCase(private val repository: Repository) {
    suspend operator fun invoke(request: ApiRequest): ApiResponse {
        return repository.verifyTokenOnBackend(request = request)
    }
}
