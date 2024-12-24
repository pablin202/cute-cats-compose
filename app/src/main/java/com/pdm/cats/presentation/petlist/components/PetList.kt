package com.pdm.cats.presentation.petlist.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdm.cats.data.dto.Cat

@Composable
fun PetList(
    pets: List<Cat>,
    onPetClicked: (Cat) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(pets) { cat ->
            PetListItem(cat = cat) {
                onPetClicked(cat)
            }
        }
    }
}