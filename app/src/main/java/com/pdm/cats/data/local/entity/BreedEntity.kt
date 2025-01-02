package com.pdm.cats.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(
    val countryCode: String,
    val countryCodes: String,
    @PrimaryKey
    val id: String,
    val lifeSpan: String,
    val name: String,
    val origin: String?,
    val temperament: String,
    val weightImperial: String,
    val weightMetric: String,
    val wikipediaUrl: String?,
    val description: String?,
    val adaptability: Int,
    val affectionLevel: Int,
    val energyLevel: Int,
    val vetstreetUrl: String?,
    val vcahospitalsUrl: String?,
    val cfaUrl: String?
)
