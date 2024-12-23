package com.pdm.cats.presentation.petlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.cats.domain.repository.PetsRepository
import com.pdm.cats.domain.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetListViewModel(
    private val petsRepository: PetsRepository
): ViewModel() {

    private val _petsUIState = MutableStateFlow(PetsUIState())
    val petsUIState: StateFlow<PetsUIState> = _petsUIState

    init {
        getPets()
    }

    private fun getPets() {
        _petsUIState.value = _petsUIState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = petsRepository.fetchCats("")) {
                is NetworkResult.Success -> {
                    _petsUIState.value = _petsUIState.value.copy(
                        cats = result.data,
                        isLoading = false
                    )
                }

                is NetworkResult.Error -> {
                    _petsUIState.value = _petsUIState.value.copy(
                        error = result.error,
                        isLoading = false
                    )
                }
            }
        }
    }
}