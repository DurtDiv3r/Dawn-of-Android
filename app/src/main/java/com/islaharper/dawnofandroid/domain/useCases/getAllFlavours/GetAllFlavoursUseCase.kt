package com.islaharper.dawnofandroid.domain.useCases.getAllFlavours

import androidx.paging.PagingData
import com.islaharper.dawnofandroid.domain.model.Flavour
import kotlinx.coroutines.flow.Flow

interface GetAllFlavoursUseCase {
    operator fun invoke(): Flow<PagingData<Flavour>>
}
