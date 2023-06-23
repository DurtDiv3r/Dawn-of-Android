package com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme

import com.islaharper.dawnofandroid.data.repository.Repository

class SaveDynamicThemeUseCase(private val repository: Repository) {
    suspend operator fun invoke(isDynamicTheme: Boolean) {
        repository.saveDynamicThemeState(isDynamicTheme = isDynamicTheme)
    }
}
