package com.pdm.cats.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class BreedModel(
    val countryCode: String,
    val countryCodes: String,
    val id: String,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val weight: WeightModel,
    val wikipediaUrl: String,
    val description: String,
): Parcelable
