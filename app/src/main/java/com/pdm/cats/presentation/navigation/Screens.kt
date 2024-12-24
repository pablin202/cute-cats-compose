package com.pdm.cats.presentation.navigation

sealed class Screens(val route: String) {
    data object PetsScreen : Screens("cats")
    data object PetDetailsScreen : Screens("catDetails")
    data object FavoritesScreen : Screens("favorites")
}