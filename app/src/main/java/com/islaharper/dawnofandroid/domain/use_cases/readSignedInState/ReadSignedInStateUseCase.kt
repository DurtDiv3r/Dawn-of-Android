package com.islaharper.dawnofandroid.domain.use_cases.readSignedInState

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadSignedInStateUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readSignedInState()
    }
}
