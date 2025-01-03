package com.pdm.cats.presentation.petlist

import com.pdm.cats.domain.models.CatModel

data class PetsUIState(
    val cats: List<CatModel> = emptyList(),
    val breeds: List<Pair<String, String>> = emptyList(),
    val currentBreed: String = "All",
    val isLoading: Boolean = false,
    val error: String? = null
)