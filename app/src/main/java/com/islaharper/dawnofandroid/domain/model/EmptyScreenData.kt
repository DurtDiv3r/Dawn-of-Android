package com.islaharper.dawnofandroid.domain.model

import androidx.paging.compose.LazyPagingItems

data class EmptyScreenData(
    val message: String,
    val icon: Int,
    val contentDescription: String? = null,
    val flavours: LazyPagingItems<Flavour>? = null
)
