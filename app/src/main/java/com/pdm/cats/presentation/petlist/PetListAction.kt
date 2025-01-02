package com.pdm.cats.presentation.petlist

import com.pdm.cats.domain.models.CatModel

interface PetListAction {
    object LoadMore : PetListAction
    data class BreedSelected(val breed: String) : PetListAction
    data class FavoriteClicked(val cat: CatModel) : PetListAction
    data class PetClicked(val cat: CatModel) : PetListAction
}