package com.pdm.cats.presentation.petdetails

import androidx.lifecycle.ViewModel
import com.pdm.cats.domain.use_cases.GetCountryFlagUrlUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PetDetailsViewModel(
    private val getCountryFlagUrlUseCase: GetCountryFlagUrlUseCase
): ViewModel() {

    private val _flagUrl = MutableStateFlow<String?>(null)
    val flagUrl: StateFlow<String?> = _flagUrl

    fun fetchFlagUrl(countryName: String) {
        val url = getCountryFlagUrlUseCase(countryName)
        _flagUrl.value = url
    }
}