package com.islaharper.dawnofandroid.domain.useCases.readDarkTheme

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadDarkModeUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readDarkModeState()
    }
}
