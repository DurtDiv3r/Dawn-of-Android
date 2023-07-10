package com.islaharper.dawnofandroid.domain.useCases.readSignedInState

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadSignedInStateUseCaseImpl @Inject constructor(private val repository: Repository) :
    ReadSignedInStateUseCase {
    override operator fun invoke(): Flow<Boolean> {
        return repository.readSignedInState()
    }
}
