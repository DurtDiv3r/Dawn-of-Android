package com.islaharper.dawnofandroid.domain.use_cases.save_signed_in_state

import com.islaharper.dawnofandroid.data.repository.Repository

class SaveSignedInStateUseCase(private val repository: Repository) {
    suspend operator fun invoke(signedIn: Boolean) {
        repository.saveSignedInState(signedIn = signedIn)
    }
}
