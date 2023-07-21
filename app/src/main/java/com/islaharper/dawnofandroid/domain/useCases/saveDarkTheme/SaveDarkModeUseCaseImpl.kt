package com.islaharper.dawnofandroid.domain.useCases.saveDarkTheme

import com.islaharper.dawnofandroid.data.repository.Repository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDarkModeUseCaseImpl @Inject constructor(private val repository: Repository) :
    SaveDarkModeUseCase {
    override suspend operator fun invoke(isDarkMode: Boolean) = flow {
        emit(repository.saveDarkModeState(isDarkMode = isDarkMode))
    }
}
