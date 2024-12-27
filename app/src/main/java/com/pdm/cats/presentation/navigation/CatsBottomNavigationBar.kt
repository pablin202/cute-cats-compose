package com.pdm.cats.presentation.navigation

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CatsBottomNavigationBar(
    onFavoriteClick: () -> Unit,
    onHomeClick: () -> Unit,
) {
    val items = listOf(PetListRoute, FavoritesRoute)
    val selectedItem = remember { mutableStateOf(items[0]) }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        NavigationBarItem(
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
        NavigationBarItem(
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