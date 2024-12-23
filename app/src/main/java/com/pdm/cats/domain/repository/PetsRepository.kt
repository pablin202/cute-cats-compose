package com.pdm.cats.domain.repository

import com.pdm.cats.data.dto.Cat
import com.pdm.cats.domain.util.NetworkResult

interface PetsRepository {
    suspend fun fetchCats(tag: String): NetworkResult<List<Cat>>
}