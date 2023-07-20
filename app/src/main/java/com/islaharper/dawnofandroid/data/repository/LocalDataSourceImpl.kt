package com.islaharper.dawnofandroid.data.repository

import com.islaharper.dawnofandroid.data.local.AndroidFlavourDb
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.domain.repository.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(flavourDb: AndroidFlavourDb) : LocalDataSource {

    private val flavourDao = flavourDb.androidFlavourDao()

    override suspend fun getSelectedFlavour(flavourId: Int): Flavour {
        return flavourDao.getSelectedFlavour(id = flavourId)
    }
}
