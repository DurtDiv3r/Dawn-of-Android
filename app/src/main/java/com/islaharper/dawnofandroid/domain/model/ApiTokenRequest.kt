package com.islaharper.dawnofandroid.domain.model

import kotlinx.serialization.Serializable

// Used to verify token for Google One Tap signin
@Serializable
data class ApiTokenRequest(
    val tokenId: String,
)
