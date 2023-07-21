package com.islaharper.dawnofandroid.domain.useCases.getAllFlavours

import androidx.paging.PagingData
import com.islaharper.dawnofandroid.data.repository.Repository
import com.islaharper.dawnofandroid.domain.model.Flavour
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFlavoursUseCaseImpl @Inject constructor(private val repository: Repository) :
    GetAllFlavoursUseCase {
    override fun invoke(): Flow<PagingData<Flavour>> {
        return repository.getAllFlavours()
    }
}
