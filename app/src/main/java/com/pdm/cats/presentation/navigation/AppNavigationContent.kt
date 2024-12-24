package com.pdm.cats.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun AppNavigationContent(
    contentType: ContentType,
    navigationType: NavigationType,
    onFavoriteClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDrawerClick: () -> Unit,
    navHostController: NavHostController
) {
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = navigationType == NavigationType.NavigationRail
        ) {
            CatsNavigationRail(
                onFavoriteClick = onFavoriteClick,
                onHomeClick = onHomeClick,
                onDrawerClick = onDrawerClick
            )
        }
        Scaffold(
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    AppNavigation(
                        contentType = contentType,
                        navHostController = navHostController
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = navigationType == NavigationType.BottomNavigation
                ) {
                    CatsBottomNavigationBar(
                        onFavoriteClick = onFavoriteClick,
                        onHomeClick = onHomeClick
                    )
                }
            }
        )
    }
}