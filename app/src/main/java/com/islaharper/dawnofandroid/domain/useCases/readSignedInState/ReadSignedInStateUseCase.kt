package com.islaharper.dawnofandroid.domain.useCases.readSignedInState

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadSignedInStateUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readSignedInState()
    }
}
