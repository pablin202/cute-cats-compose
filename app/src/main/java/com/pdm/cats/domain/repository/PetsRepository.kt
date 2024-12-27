package com.pdm.cats.domain.repository

import com.pdm.cats.domain.models.CatModel
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    suspend fun getCats(page: Int): Flow<List<CatModel>>
    suspend fun fetchRemoteCats()
    suspend fun updatePet(cat: CatModel)
    suspend fun getFavoriteCats(): Flow<List<CatModel>>
}