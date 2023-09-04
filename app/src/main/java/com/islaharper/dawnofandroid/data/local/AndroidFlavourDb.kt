package com.islaharper.dawnofandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.islaharper.dawnofandroid.data.local.dao.AndroidFlavourDao
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.util.Constants.FLAVOUR_DB

@Database(entities = [Flavour::class], version = 1)
abstract class AndroidFlavourDb : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): AndroidFlavourDb {
            val dbBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(
                    context,
                    AndroidFlavourDb::class.java,
                )
            } else {
                Room.databaseBuilder(context, AndroidFlavourDb::class.java, FLAVOUR_DB)
            }
            return dbBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun androidFlavourDao(): AndroidFlavourDao
}
