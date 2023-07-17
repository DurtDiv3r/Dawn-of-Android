package com.islaharper.dawnofandroid.domain.repository

import com.islaharper.dawnofandroid.domain.model.Flavour

interface LocalDataSource {
    suspend fun getSelectedFlavour(flavourId: Int): Flavour
}
