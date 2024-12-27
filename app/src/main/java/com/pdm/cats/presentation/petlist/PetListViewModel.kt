package com.pdm.cats.presentation.petlist

import android.os.Debug
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.repository.PetsRepository
import com.pdm.cats.domain.util.NetworkResult
import com.pdm.cats.domain.util.asResult
import com.pdm.cats.domain.util.stateInWhileSubscribed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PetListViewModel(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _currentPage = MutableStateFlow(0)

    private val _petsUIState = MutableStateFlow(PetsUIState())
    val petsUIState: StateFlow<PetsUIState> = _petsUIState

    private val _favoritePets = MutableStateFlow<List<CatModel>>(emptyList())
    val favoritePets: StateFlow<List<CatModel>> get() = _favoritePets

    @OptIn(ExperimentalCoroutinesApi::class)
    val cats: StateFlow<List<CatModel>> = _currentPage
        .filter { !_petsUIState.value.isLoading }
        .flatMapLatest { page ->
            petsRepository.getCats(page).asResult().map {
                when (it) {
                    is NetworkResult.Success -> {
                        Log.d("CATS", it.data.toString())
                        _petsUIState.value = _petsUIState.value.copy(
                            isLoading = false,
                            cats = _petsUIState.value.cats + it.data
                        )
                        _petsUIState.value.cats + it.data
                    }

                    is NetworkResult.Error -> {
                        _petsUIState.value = _petsUIState.value.copy(
                            isLoading = false,
                            error = it.error
                        )
                        _petsUIState.value.cats
                    }
                }
            }
        }
        .stateInWhileSubscribed(initialValue = emptyList())

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

    fun loadMore() {
        if (_petsUIState.value.isLoading) return
        _currentPage.value++
    }
}