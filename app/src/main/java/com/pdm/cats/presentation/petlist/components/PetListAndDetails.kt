package com.pdm.cats.presentation.petlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdm.cats.data.dto.Cat
import com.pdm.cats.presentation.petdetails.PetDetailsScreenContent

@Composable
fun PetListAndDetails(
    cats: List<Cat>
) {
    var currentPet by remember {
        mutableStateOf(cats.first())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PetList(
            onPetClicked = {
                currentPet = it
            },
            pets = cats,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        PetDetailsScreenContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            cat = currentPet
        )
    }
}