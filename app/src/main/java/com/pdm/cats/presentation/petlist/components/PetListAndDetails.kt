package com.pdm.cats.presentation.petlist.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.presentation.petdetails.PetDetailsScreenContent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetListAndDetails(
    animatedVisibilityScope: AnimatedVisibilityScope,
    cats: List<CatModel>,
    loadMore: () -> Unit
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
            animatedVisibilityScope = animatedVisibilityScope,
            onPetClicked = {
                currentPet = it
            },
            pets = cats,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            loadMore = {
                loadMore()
            }
        )
        PetDetailsScreenContent(
            animatedVisibilityScope = animatedVisibilityScope,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            imageUrl = currentPet.url,
            name = currentPet.breeds[0].name,
            description = currentPet.breeds[0].description,
            temperament = currentPet.breeds[0].temperament,
            adaptability = currentPet.breeds[0].adaptability,
            affectionLevel = currentPet.breeds[0].affectionLevel,
            energyLevel = currentPet.breeds[0].energyLevel,
            vetstreetUrl = currentPet.breeds[0].vetstreetUrl,
            cfaUrl = currentPet.breeds[0].cfaUrl,
            vcahospitalsUrl = currentPet.breeds[0].vcahospitalsUrl,
            weight = currentPet.breeds[0].weight.metric,
            origin = currentPet.breeds[0].origin,
            lifeSpan = currentPet.breeds[0].lifeSpan,
            id = currentPet.id
        )
    }
}