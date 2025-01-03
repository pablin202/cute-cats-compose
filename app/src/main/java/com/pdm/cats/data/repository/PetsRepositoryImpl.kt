package com.pdm.cats.data.repository

import android.util.Log
import com.pdm.cats.data.local.dao.BreedDao
import com.pdm.cats.data.local.dao.CatDao
import com.pdm.cats.data.networking.CatsApi
import com.pdm.cats.domain.mappers.toEntity
import com.pdm.cats.domain.mappers.toModel
import com.pdm.cats.domain.models.BreedModel
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.repository.PetsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PetsRepositoryImpl(
    private val catsApi: CatsApi,
    private val dispatcher: CoroutineDispatcher,
    private val catDao: CatDao,
    private val breedDao: BreedDao
) : PetsRepository {

    override suspend fun getPaginationCount() {
        val response = catsApi.fetchCats("0")
        if (response.isSuccessful) {
            val headers = response.headers()
            val paginationCount = headers["pagination-count"]
        } else {
        }
    }

    override suspend fun getCatsByBreed(breed: String): Flow<List<CatModel>> = flow {
        val response = catsApi.fetchCatsByBreed(breedIds = breed)
        if (response.isSuccessful) {
            val catDtoList = response.body() ?: emptyList()
            val catModels = catDtoList
                .filter { dto -> dto.breeds.isNotEmpty() }
                .distinctBy { dto -> dto.url }
                .map { dto -> dto.toModel() }
            Log.d("CatsRepositoryImpl", "Cats by breed: $catModels")
            emit(catModels)
        } else {
            throw Exception(
                "Failed to fetch cats by breed: ${response.code()} - ${response.message()}"
            )
        }
    }.flowOn(dispatcher)

    override suspend fun getCats(page: Int): Flow<List<CatModel>> = flow {
        val response = catsApi.fetchCats(page.toString())
        if (response.isSuccessful) {
            val catDtoList = response.body() ?: emptyList()
            val catModels = catDtoList
                .filter { dto -> dto.breeds.isNotEmpty() }
                .distinctBy { dto -> dto.url }
                .map { dto -> dto.toModel() }
            emit(catModels)
        } else {
            throw Exception("Failed to fetch cats: ${response.code()} - ${response.message()}")
        }
    }.flowOn(dispatcher)

    override suspend fun fetchRemoteCats() {
        TODO("Not yet implemented")
    }

    override suspend fun updatePet(cat: CatModel) {
//        withContext(dispatcher) {
//            catDao.update(
//                CatEntity(
//                    id = cat.id,
//                    tags = cat.tags.joinToString(","),
//                    isFavorite = cat.isFavorite
//                )
//            )
//        }
    }

    override suspend fun getFavoriteCats(): Flow<List<CatModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreeds(): Flow<List<BreedModel>> = flow {
        var cachedBreeds = breedDao.getAllBreads().first()
        if (cachedBreeds.isNotEmpty()) {
            emit(cachedBreeds.map { it.toModel() })
        } else {
            val breedsFromNetwork = catsApi.fetchBreeds()

            if (breedsFromNetwork.isSuccessful) {
                val breedDtoList = breedsFromNetwork.body() ?: emptyList()
                val breedEntities = breedDtoList.map { it.toEntity() }
                breedDao.insertBreeds(breedEntities)
                cachedBreeds = breedDao.getAllBreads().first()
                emit(cachedBreeds.map { it.toModel() })
            } else {
                throw Exception(
                    "Failed to fetch breeds: ${breedsFromNetwork.code()} - ${breedsFromNetwork.message()}"
                )
            }
        }
    }.flowOn(dispatcher)
}