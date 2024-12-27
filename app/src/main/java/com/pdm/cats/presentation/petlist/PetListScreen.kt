package com.pdm.cats.presentation.petlist

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.presentation.components.CustomTopBar
import com.pdm.cats.presentation.components.LoadingAnimation
import com.pdm.cats.presentation.navigation.ContentType
import com.pdm.cats.presentation.petlist.components.PetList
import com.pdm.cats.presentation.petlist.components.PetListAndDetails
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    contentType: ContentType,
    onPetClicked: (CatModel) -> Unit
) {
    val viewModel: PetListViewModel = koinViewModel()
    val petsUIState by viewModel.petsUIState.collectAsStateWithLifecycle()
    val cats by viewModel.cats.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopBar("Cute Cats")
        },
        modifier = Modifier.fillMaxSize()
    ) {
        PetListContent(
            animatedVisibilityScope = animatedVisibilityScope,
            contentType = contentType,
            state = petsUIState,
            loadMore = {
                viewModel.loadMore()
            },
        ) {
            onPetClicked(it)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetListContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    contentType: ContentType,
    state: PetsUIState,
    loadMore: () -> Unit,
    onPetClicked: (CatModel) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = state.isLoading) {
            LoadingAnimation()
        }
        AnimatedVisibility(visible = state.cats.isNotEmpty()) {
            if (contentType == ContentType.List) {
                PetList(
                    animatedVisibilityScope = animatedVisibilityScope,
                    pets = state.cats,
                    onPetClicked = onPetClicked,
                    modifier = Modifier.fillMaxSize()
                ) {
                    loadMore()
                }
            } else {
                PetListAndDetails(
                    animatedVisibilityScope = animatedVisibilityScope,
                    cats =
                    state.cats
                ) { loadMore() }
            }
        }
        AnimatedVisibility(visible = state.error != null) {
            Text(text = state.error ?: "Unknown error", color = Color.Red)
        }
    }
}

//@OptIn(ExperimentalSharedTransitionApi::class)
//@Preview(showBackground = true)
//@Composable
//fun PetListScreenPreview() {
//    SharedTransitionLayout { animatedVisibilityScope ->
//        PetListScreen(
//            animatedVisibilityScope = animatedVisibilityScope,
//            contentType = ContentType.List
//        ) {
//
//        }
//    }
//}
