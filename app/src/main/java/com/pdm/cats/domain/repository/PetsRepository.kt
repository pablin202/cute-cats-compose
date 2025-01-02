package com.pdm.cats.domain.repository

import com.pdm.cats.domain.models.BreedModel
import com.pdm.cats.domain.models.CatModel
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    suspend fun getPaginationCount()
    suspend fun getCats(page: Int): Flow<List<CatModel>>
    suspend fun getCatsByBreed(breed: String): Flow<List<CatModel>>
    suspend fun fetchRemoteCats()
    suspend fun updatePet(cat: CatModel)
    suspend fun getFavoriteCats(): Flow<List<CatModel>>
    suspend fun getBreeds(): Flow<List<BreedModel>>
}