package com.islaharper.dawnofandroid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * RemoteKeys Data Class:
 * Used for storing Paging data for offline use.
 *
 * @property prevPage is the previous page data.
 * @property nextPage is the following page data.
 * @property lastUpdate is timestamp when data was last refreshed.
 */

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdate: Long?,
)
