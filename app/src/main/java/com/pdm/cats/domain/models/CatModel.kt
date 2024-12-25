package com.pdm.cats.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CatModel(
    val breeds: List<BreedModel>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int,
    val isFavorite: Boolean
) : Parcelable
