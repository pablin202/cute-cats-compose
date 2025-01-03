package com.pdm.cats.presentation.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun CatsNavigationRail(
    onFavoriteClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDrawerClick: () -> Unit
) {
    val items = listOf(PetListRoute, FavoritesRoute)
    val selectedItem = remember { mutableStateOf(items[0]) }

    NavigationRail(
        modifier = Modifier.fillMaxHeight()
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon"
                )
            }
        )
        NavigationRailItem(
            selected = selectedItem.value == PetListRoute,
            onClick = {
                onHomeClick()
                selectedItem.value = PetListRoute
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )
        NavigationRailItem(
            selected = selectedItem.value == FavoritesRoute,
            onClick = {
                onFavoriteClick()
                selectedItem.value = FavoritesRoute
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )
    }
}