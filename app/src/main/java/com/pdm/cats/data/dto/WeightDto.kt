package com.pdm.cats.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeightDto(
    val imperial: String,
    val metric: String
)