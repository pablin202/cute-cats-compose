package com.pdm.cats.presentation.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CatsBottomNavigationBar(
    onFavoriteClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val items = listOf(PetListRoute, FavoritesRoute)
    val selectedItem = remember { mutableStateOf(items[0]) }
    NavigationBar(
        modifier = Modifier.graphicsLayer {
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
            clip = true
        },
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
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