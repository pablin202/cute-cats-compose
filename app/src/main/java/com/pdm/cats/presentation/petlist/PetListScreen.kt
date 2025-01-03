package com.pdm.cats.presentation.petlist

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pdm.cats.R
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.presentation.components.CustomTopBar
import com.pdm.cats.presentation.components.LoadingAnimation
import com.pdm.cats.presentation.navigation.ContentType
import com.pdm.cats.presentation.petlist.components.HorizontalSelectableButtons
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

    Scaffold(
        topBar = {
            CustomTopBar(stringResource(R.string.title_list_cute_cats))
        },
        modifier = Modifier.fillMaxSize()
    ) {
        PetListContent(
            animatedVisibilityScope = animatedVisibilityScope,
            contentType = contentType,
            state = petsUIState,
            onAction = { action ->
                when (action) {
                    is PetListAction.PetClicked -> {
                        onPetClicked(action.cat)
                    }

                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetListContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    contentType: ContentType,
    state: PetsUIState,
    onAction: (PetListAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 106.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = state.isLoading) {
            LoadingAnimation()
        }
        AnimatedVisibility(visible = state.cats.isNotEmpty()) {
            if (contentType == ContentType.List) {
                HorizontalSelectableButtons(
                    Modifier.padding(top = 8.dp),
                    items = state.breeds,
                    selectedValue = state.currentBreed,
                    onSelectionChanged = { onAction(PetListAction.BreedSelected(it)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PetList(
                    animatedVisibilityScope = animatedVisibilityScope,
                    pets = state.cats,
                    onPetClicked = { onAction(PetListAction.PetClicked(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 68.dp)
                        .weight(1f)
                ) {
                    onAction(PetListAction.LoadMore)
                }
            } else {
                PetListAndDetails(
                    animatedVisibilityScope = animatedVisibilityScope,
                    cats =
                    state.cats
                ) { onAction(PetListAction.LoadMore) }
            }
        }
        AnimatedVisibility(visible = state.error != null) {
            Text(text = state.error ?: stringResource(R.string.unknown_error), color = Color.Red)
        }
    }
}

// @OptIn(ExperimentalSharedTransitionApi::class)
// @Preview(showBackground = true)
// @Composable
// fun PetListScreenPreview() {
//    SharedTransitionLayout { animatedVisibilityScope ->
//        PetListScreen(
//            animatedVisibilityScope = animatedVisibilityScope,
//            contentType = ContentType.List
//        ) {
//
//        }
//    }
// }