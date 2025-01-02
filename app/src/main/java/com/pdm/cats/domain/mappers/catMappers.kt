package com.pdm.cats.domain.mappers

import androidx.compose.ui.Modifier
import com.pdm.cats.data.dto.BreedDto
import com.pdm.cats.data.dto.CatDto
import com.pdm.cats.data.dto.WeightDto
import com.pdm.cats.data.local.entity.BreedEntity
import com.pdm.cats.domain.models.BreedModel
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.models.WeightModel

fun WeightDto.toModel(): WeightModel {
    return WeightModel(
        imperial = this.imperial,
        metric = this.metric
    )
}

fun BreedDto.toModel(): BreedModel {
    return BreedModel(
        id = this.id,
        name = this.name,
        origin = this.origin,
        temperament = this.temperament,
        lifeSpan = this.lifeSpan,
        countryCode = this.countryCode,
        countryCodes = this.countryCodes,
        wikipediaUrl = this.wikipediaUrl,
        weight = this.weight.toModel(),
        description = this.description,
        adaptability = this.adaptability,
        affectionLevel = this.affectionLevel,
        energyLevel = this.energyLevel,
        vetstreetUrl = this.vetstreetUrl,
        vcahospitalsUrl = this.vcahospitalsUrl,
        cfaUrl = this.cfaUrl
    )
}

fun BreedDto.toEntity(): BreedEntity {
    return BreedEntity(
        id = this.id,
        name = this.name,
        origin = this.origin,
        temperament = this.temperament,
        lifeSpan = this.lifeSpan,
        countryCode = this.countryCode,
        countryCodes = this.countryCodes,
        wikipediaUrl = this.wikipediaUrl,
        weightImperial = this.weight.imperial,
        weightMetric = this.weight.metric,
        description = this.description,
        adaptability = this.adaptability,
        affectionLevel = this.affectionLevel,
        energyLevel = this.energyLevel,
        vetstreetUrl = this.vetstreetUrl,
        vcahospitalsUrl = this.vcahospitalsUrl,
        cfaUrl = this.cfaUrl
    )
}

fun BreedEntity.toModel(): BreedModel {
    return BreedModel(
        id = this.id,
        name = this.name,
        origin = this.origin,
        temperament = this.temperament,
        lifeSpan = this.lifeSpan,
        countryCode = this.countryCode,
        countryCodes = this.countryCodes,
        wikipediaUrl = this.wikipediaUrl,
        weight = WeightModel(this.weightImperial, this.weightMetric),
        description = this.description,
        adaptability = this.adaptability,
        affectionLevel = this.affectionLevel,
        energyLevel = this.energyLevel,
        vetstreetUrl = this.vetstreetUrl,
        vcahospitalsUrl = this.vcahospitalsUrl,
        cfaUrl = this.cfaUrl
    )
}


fun CatDto.toModel(): CatModel {
    return CatModel(
        id = this.id,
        url = this.url,
        height = this.height,
        width = this.width,
        breeds = this.breeds.map { it.toModel() },
        isFavorite = false
    )
}