package com.pdm.cats.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class WeightModel(
    val imperial: String,
    val metric: String
) : Parcelable