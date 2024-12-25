package com.pdm.cats.presentation.petlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.cats.data.dto.Cat
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.repository.PetsRepository
import com.pdm.cats.domain.util.NetworkResult
import com.pdm.cats.domain.util.asResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetListViewModel(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _petsUIState = MutableStateFlow(PetsUIState())
    val petsUIState: StateFlow<PetsUIState> = _petsUIState

    private val _favoritePets = MutableStateFlow<List<CatModel>>(emptyList())
    val favoritePets: StateFlow<List<CatModel>> get() = _favoritePets

    init {
        getPets()
    }

    private fun getPets() {
        _petsUIState.value = _petsUIState.value.copy(isLoading = true)
        viewModelScope.launch {
            petsRepository.getCats().asResult().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _petsUIState.value = _petsUIState.value.copy(
                            isLoading = false,
                            cats = it.data
                        )
                    }

                    is NetworkResult.Error -> {
                        _petsUIState.value = _petsUIState.value.copy(
                            isLoading = false,
                            error = it.error
                        )
                    }
                }
            }
        }
    }

    fun updateCat(cat: CatModel) {
        viewModelScope.launch {
            petsRepository.updatePet(cat)
        }
    }

    fun getFavoriteCats() {
        viewModelScope.launch {
            petsRepository.getFavoriteCats().collect {
                _favoritePets.value = it
            }
        }
    }
}