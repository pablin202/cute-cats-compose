package com.pdm.cats.presentation.petlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pdm.cats.data.dto.Cat
import com.pdm.cats.presentation.components.LoadingAnimation
import com.pdm.cats.presentation.petlist.components.PetListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(
    onPetClicked: (Cat) -> Unit
) {
    val viewModel: PetListViewModel = koinViewModel()
    val petsUIState by viewModel.petsUIState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cats") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        PetListContent(
            state = petsUIState,
            modifier = Modifier.padding(innerPadding),
        ) {
            onPetClicked(it)
        }
    }
}

@Composable
fun PetListContent(
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
            LazyColumn {
                items(state.cats) { cat ->
                    PetListItem(cat = cat) {
                        onPetClicked(cat)
                    }
                }
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
    PetListScreen() {

    }
}
