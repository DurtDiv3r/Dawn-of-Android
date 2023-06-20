package com.islaharper.dawnofandroid.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val emailAddress: String,
    val picture: String,
    val flavours: List<Flavour>,
)
