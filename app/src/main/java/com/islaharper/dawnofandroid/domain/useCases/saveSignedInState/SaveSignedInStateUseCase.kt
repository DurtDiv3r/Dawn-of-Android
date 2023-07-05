package com.islaharper.dawnofandroid.domain.useCases.saveSignedInState

import com.islaharper.dawnofandroid.data.repository.Repository
import javax.inject.Inject

class SaveSignedInStateUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(signedIn: Boolean): Boolean {
        return repository.saveSignedInState(signedIn = signedIn)
    }
}
