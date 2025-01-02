package com.pdm.cats.presentation.petlist.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.pdm.cats.domain.models.CatModel
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PetList(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pets: List<CatModel>,
    onPetClicked: (CatModel) -> Unit,
    modifier: Modifier = Modifier,
    loadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(pets, key = { it.id }) { cat ->
            PetListItem(
                animatedVisibilityScope,
                cat = cat
            ) {
                onPetClicked(cat)
            }
        }
    }

    LaunchedEffect(listState, pets) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleItemIndex ->
                if (!isLoading && lastVisibleItemIndex != null && lastVisibleItemIndex >= pets.size - 3) {
                    isLoading = true
                    loadMore()
                    isLoading = false
                }
            }
    }
}