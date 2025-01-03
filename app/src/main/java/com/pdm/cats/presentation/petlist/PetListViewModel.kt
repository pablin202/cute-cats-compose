package com.pdm.cats.presentation.petlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _currentPage = MutableStateFlow(0)

    private val _petsUIState = MutableStateFlow(PetsUIState())
    val petsUIState: StateFlow<PetsUIState> = _petsUIState

    private val _favoritePets = MutableStateFlow<List<CatModel>>(emptyList())
    val favoritePets: StateFlow<List<CatModel>> get() = _favoritePets

    init {
        if (_petsUIState.value.cats.isEmpty()) {
            _petsUIState.value = _petsUIState.value.copy(isLoading = true)
            getBreeds()
            getPets()
        }
    }

    private fun getBreeds() {
        viewModelScope.launch {
            petsRepository.getBreeds().asResult().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _petsUIState.value = _petsUIState.value.copy(
                            breeds = it.data.map { breed -> breed.name to breed.id }
                        )
                    }

                    is NetworkResult.Error -> {
                    }
                }
            }
        }
    }

    fun getPets() {
        viewModelScope.launch {
            petsRepository.getCats(_currentPage.value).asResult().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _petsUIState.value = _petsUIState.value.copy(
                            isLoading = false,
                            cats = _petsUIState.value.cats + it.data
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

    private fun getPetsByBreed() {
        viewModelScope.launch {
            _petsUIState.value = _petsUIState.value.copy(isLoading = true)
            petsRepository.getCatsByBreed(_petsUIState.value.currentBreed).asResult().collect {
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

    fun onAction(action: PetListAction) {
        when (action) {
            PetListAction.LoadMore -> {
                if (_petsUIState.value.isLoading) return
                _currentPage.value++
                getPets()
            }

            is PetListAction.BreedSelected -> {
                if (_petsUIState.value.currentBreed == action.breed) return
                _petsUIState.value =
                    _petsUIState.value.copy(currentBreed = action.breed, cats = emptyList())
                _currentPage.value = 0

                if (action.breed == "All") {
                    getPets()
                } else {
                    getPetsByBreed()
                }
            }

            else -> Unit
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