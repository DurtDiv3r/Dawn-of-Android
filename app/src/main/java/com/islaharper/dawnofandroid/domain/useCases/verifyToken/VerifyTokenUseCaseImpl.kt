package com.islaharper.dawnofandroid.domain.useCases.verifyToken

import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyTokenUseCaseImpl @Inject constructor(private val repository: Repository) :
    VerifyTokenUseCase {
    override suspend operator fun invoke(request: ApiTokenRequest) = flow {
        emit(repository.verifyToken(request = request))
    }
}
