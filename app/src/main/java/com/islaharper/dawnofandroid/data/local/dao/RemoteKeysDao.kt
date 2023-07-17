package com.islaharper.dawnofandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.islaharper.dawnofandroid.domain.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM flavour_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(flavourRemoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM flavour_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
