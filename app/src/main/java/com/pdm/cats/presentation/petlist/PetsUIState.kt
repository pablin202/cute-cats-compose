package com.pdm.cats.presentation.petlist

import com.pdm.cats.data.dto.Cat

data class PetsUIState(
    val cats: List<Cat> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
