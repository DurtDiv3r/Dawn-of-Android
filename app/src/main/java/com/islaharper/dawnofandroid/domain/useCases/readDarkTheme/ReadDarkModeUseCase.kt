package com.islaharper.dawnofandroid.domain.useCases.readDarkTheme

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadDarkModeUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readDarkModeState()
    }
}
