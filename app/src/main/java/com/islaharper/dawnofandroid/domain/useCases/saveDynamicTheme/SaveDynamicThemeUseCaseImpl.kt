package com.islaharper.dawnofandroid.domain.useCases.saveDynamicTheme

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDynamicThemeUseCaseImpl @Inject constructor(private val repository: Repository) :
    SaveDynamicThemeUseCase {
    override suspend operator fun invoke(isDynamicTheme: Boolean) = flow {
        emit(repository.saveDynamicThemeState(isDynamicTheme = isDynamicTheme))
    }
}
