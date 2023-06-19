package com.islaharper.dawnofandroid.domain.use_cases.read_signed_in_state

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadSignedInStateUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readSignedInState()
    }
}
