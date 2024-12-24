package com.pdm.cats.presentation.petlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pdm.cats.data.dto.Cat
import com.pdm.cats.presentation.components.CustomTopBar
import com.pdm.cats.presentation.components.LoadingAnimation
import com.pdm.cats.presentation.navigation.ContentType
import com.pdm.cats.presentation.petlist.components.PetList
import com.pdm.cats.presentation.petlist.components.PetListAndDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetListScreen(
    contentType: ContentType,
    onPetClicked: (Cat) -> Unit
) {
    val viewModel: PetListViewModel = koinViewModel()
    val petsUIState by viewModel.petsUIState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopBar("Cute Cats")
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        PetListContent(
            contentType = contentType,
            state = petsUIState,
            modifier = Modifier.padding(innerPadding),
        ) {
            onPetClicked(it)
        }
    }
}

@Composable
fun PetListContent(
    contentType: ContentType,
    state: PetsUIState,
    modifier: Modifier,
    onPetClicked: (Cat) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = state.isLoading) {
            LoadingAnimation()
        }
        AnimatedVisibility(visible = state.cats.isNotEmpty()) {
            if (contentType == ContentType.List) {
                PetList(
                    pets = state.cats,
                    onPetClicked = onPetClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                PetListAndDetails(state.cats)
            }
        }
        AnimatedVisibility(visible = state.error != null) {
            Text(text = state.error ?: "Unknown error", color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetListScreenPreview() {
    PetListScreen(
        contentType = ContentType.List
    ) {

    }
}
