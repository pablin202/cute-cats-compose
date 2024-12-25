package com.pdm.cats.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatDto(
    val breeds: List<BreedDto>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)