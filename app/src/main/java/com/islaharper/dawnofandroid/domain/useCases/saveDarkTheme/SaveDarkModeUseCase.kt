package com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme

import kotlinx.coroutines.flow.Flow

interface SaveDarkModeUseCase {
    suspend operator fun invoke(isDarkMode: Boolean): Flow<Boolean>
}
