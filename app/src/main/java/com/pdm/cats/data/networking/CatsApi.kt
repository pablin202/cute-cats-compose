package com.pdm.cats.data.networking

import com.pdm.cats.data.dto.BreedDto
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
        @Query("order") order: String = "RAND"
    ): Response<List<CatDto>>

    @GET("images/search")
    suspend fun fetchCatsByBreed(
        @Query("limit") limit: String = "20",
        @Query("breed_ids") breedIds: String
    ): Response<List<CatDto>>

    @GET("breeds")
    suspend fun fetchBreeds(): Response<List<BreedDto>>
}