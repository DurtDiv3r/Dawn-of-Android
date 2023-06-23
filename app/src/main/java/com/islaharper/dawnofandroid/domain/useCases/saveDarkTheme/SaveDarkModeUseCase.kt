package com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme

import com.islaharper.dawnofandroid.data.repository.Repository

class SaveDarkModeUseCase(private val repository: Repository) {
    suspend operator fun invoke(isDarkMode: Boolean) {
        repository.saveDarkModeState(isDarkMode = isDarkMode)
    }
}
