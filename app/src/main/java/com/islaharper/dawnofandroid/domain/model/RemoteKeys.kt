package com.islaharper.dawnofandroid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.islaharper.dawnofandroid.util.Constants.REMOTE_KEYS_TABLE

@Entity(tableName = REMOTE_KEYS_TABLE)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdate: Long?,
)
