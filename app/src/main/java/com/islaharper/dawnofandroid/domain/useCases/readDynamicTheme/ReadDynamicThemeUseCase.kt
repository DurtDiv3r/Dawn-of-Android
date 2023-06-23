package com.islaharper.dawnofandroid.domain.useCases.readDynamicTheme

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadDynamicThemeUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readDynamicThemeState()
    }
}
