package com.islaharper.dawnofandroid.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val responseData: ResponseData? = null,
)
