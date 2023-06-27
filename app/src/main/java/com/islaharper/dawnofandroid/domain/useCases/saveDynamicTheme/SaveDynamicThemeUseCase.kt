package com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme

import com.islaharper.dawnofandroid.data.repository.Repository
import javax.inject.Inject

class SaveDynamicThemeUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(isDynamicTheme: Boolean) {
        repository.saveDynamicThemeState(isDynamicTheme = isDynamicTheme)
    }
}
