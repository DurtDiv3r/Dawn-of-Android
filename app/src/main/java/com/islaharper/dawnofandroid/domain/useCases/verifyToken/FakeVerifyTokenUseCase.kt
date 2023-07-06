package com.islaharper.dawnofandroid.domain.useCases.verifyToken

import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class FakeVerifyTokenUseCase : VerifyTokenUseCase {
    private val fakeFlow = MutableStateFlow<Resource<ApiResponse>>(Resource.Idle)
    suspend fun emit(value: Resource<ApiResponse>) = fakeFlow.emit(value)
    override suspend fun invoke(request: ApiTokenRequest) = fakeFlow
}
