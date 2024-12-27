package com.pdm.cats.presentation.petlist.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.pdm.cats.domain.models.CatModel

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
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(pets) { cat ->
            PetListItem(
                animatedVisibilityScope,
                cat = cat
            ) {
                onPetClicked(cat)
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= pets.size - 3) {
                    loadMore()
                }
            }
    }
}