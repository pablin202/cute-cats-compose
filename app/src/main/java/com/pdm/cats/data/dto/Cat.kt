package com.pdm.cats.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    @SerialName("_id")
    val id: String,
    @SerialName("tags")
    val tags: List<String>,
    val isFavorite: Boolean = false
)