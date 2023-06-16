package com.islaharper.dawnofandroid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.islaharper.dawnofandroid.util.Constants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Constants.FLAVOUR_DB_TABLE)
data class Flavour(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    val version: String,
    val name: String,
    val description: String,
    val imageSmall: String,
    val imageLarge: String,
    val rating: Double,
    val year: String,
)
