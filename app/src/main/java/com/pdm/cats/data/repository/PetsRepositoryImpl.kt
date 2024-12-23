package com.pdm.cats.data.repository

import com.pdm.cats.data.dto.Cat
import com.pdm.cats.data.networking.CatsApi
import com.pdm.cats.domain.repository.PetsRepository
import com.pdm.cats.domain.util.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PetsRepositoryImpl(
    private val catsApi: CatsApi,
    private val ioDispatcher: CoroutineDispatcher
) : PetsRepository {
    override suspend fun fetchCats(tag: String): NetworkResult<List<Cat>> {
        return withContext(ioDispatcher) {
            try {
                val response = catsApi.fetchCats(tag)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body() ?: emptyList())
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}