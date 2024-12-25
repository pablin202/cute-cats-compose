package com.pdm.cats.data.networking

import com.pdm.cats.data.dto.CatDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {
    @GET("images/search")
    suspend fun fetchCats(
        @Query("page") page: String,
        @Query("limit") limit: String = "20",
        @Query("has_breeds") hasBreeds: String = "1",
        @Query("order") order: String = "DESC"
    ): Response<List<CatDto>>
}