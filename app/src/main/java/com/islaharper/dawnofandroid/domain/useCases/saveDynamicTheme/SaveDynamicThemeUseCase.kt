package com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme

import kotlinx.coroutines.flow.Flow

interface SaveDynamicThemeUseCase {
    suspend operator fun invoke(isDynamicTheme: Boolean): Flow<Boolean>
}
