package com.pdm.cats.data.networking

import com.pdm.cats.data.dto.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {
    @GET("cats")
    suspend fun fetchCats(
        @Query("tag") tag: String,
    ): Response<List<Cat>>
}