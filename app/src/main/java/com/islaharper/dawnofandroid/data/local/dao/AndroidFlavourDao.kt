package com.islaharper.dawnofandroid.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.islaharper.dawnofandroid.domain.model.Flavour

@Dao
interface AndroidFlavourDao {

    @Query("SELECT * FROM flavour_table ORDER BY id ASC")
    fun getAllFlavours(): PagingSource<Int, Flavour>

    @Query("SELECT * FROM flavour_table WHERE id = :id")
    fun getSelectedFlavour(id: Int): Flavour

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFlavours(flavours: List<Flavour>)

    @Query("DELETE FROM flavour_table")
    suspend fun deleteAll()
}
