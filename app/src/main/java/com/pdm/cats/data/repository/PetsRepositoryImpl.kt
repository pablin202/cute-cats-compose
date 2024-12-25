package com.pdm.cats.data.repository

import com.pdm.cats.data.dto.Cat
import com.pdm.cats.data.local.dao.CatDao
import com.pdm.cats.data.local.entity.CatEntity
import com.pdm.cats.data.networking.CatsApi
import com.pdm.cats.domain.mappers.toModel
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.repository.PetsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class PetsRepositoryImpl(
    private val catsApi: CatsApi,
    private val dispatcher: CoroutineDispatcher,
    private val catDao: CatDao
) : PetsRepository {

    override suspend fun getCats(): Flow<List<CatModel>> = flow {
        val response = catsApi.fetchCats("1")
        if (response.isSuccessful) {
            val catDtoList = response.body() ?: emptyList()
            val catModels = catDtoList.map { dto -> dto.toModel() }
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
}