package com.islaharper.dawnofandroid.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val user: User? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val flavours: List<Flavour>? = null,
    val lastUpdated: Long? = null,
)
