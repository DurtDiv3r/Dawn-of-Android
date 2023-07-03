package com.islaharper.dawnofandroid.domain.model

sealed class MessageBarState(open val message: String) {
    // Idle state for initialisation in ViewModel
    object Idle : MessageBarState("")
    data class Success(override val message: String) : MessageBarState(message)
    data class Failure(override val message: String) : MessageBarState(message)
}
