package com.pdm.cats.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdm.cats.presentation.components.CustomTopBar

@Composable
fun FavoritesScreen() {
    Scaffold(
        topBar = {
            CustomTopBar("Favorites")
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Text(text = "Favorites")
        }
    }
}