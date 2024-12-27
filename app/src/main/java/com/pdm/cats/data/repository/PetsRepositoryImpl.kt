package com.pdm.cats.data.repository

import android.os.Debug
import com.pdm.cats.data.local.dao.CatDao
import com.pdm.cats.data.networking.CatsApi
import com.pdm.cats.domain.mappers.toModel
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.repository.PetsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PetsRepositoryImpl(
    private val catsApi: CatsApi,
    private val dispatcher: CoroutineDispatcher,
    private val catDao: CatDao
) : PetsRepository {

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
}