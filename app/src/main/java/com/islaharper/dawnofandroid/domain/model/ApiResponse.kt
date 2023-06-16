package com.islaharper.dawnofandroid.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ApiResponse(
    val success: Boolean,
    val user: User? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val flavours: List<Flavour> = emptyList(),
    val lastUpdated: Long? = null,
)
