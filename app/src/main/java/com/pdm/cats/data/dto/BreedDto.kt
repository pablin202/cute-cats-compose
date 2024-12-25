package com.pdm.cats.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("country_codes")
    val countryCodes: String,
    val id: String,
    @SerialName("life_span")
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val weight: WeightDto,
    val description: String,
    @SerialName("wikipedia_url")
    val wikipediaUrl: String
)