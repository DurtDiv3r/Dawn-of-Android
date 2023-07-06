package com.islaharper.dawnofandroid.domain.useCases.saveSignedInState

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveSignedInStateUseCaseImpl @Inject constructor(private val repository: Repository) :
    SaveSignedInStateUseCase {
    override suspend operator fun invoke(signedIn: Boolean) = flow {
        emit(repository.saveSignedInState(signedIn = signedIn))
    }
}
