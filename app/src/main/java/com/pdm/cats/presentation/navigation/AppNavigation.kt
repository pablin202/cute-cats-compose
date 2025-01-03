package com.pdm.cats.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.presentation.favorites.FavoritesScreen
import com.pdm.cats.presentation.petdetails.PetDetailsScreen
import com.pdm.cats.presentation.petlist.PetListScreen
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = PetListRoute
        ) {
            composable<PetListRoute> {
                PetListScreen(
                    animatedVisibilityScope = this,
                    contentType = contentType
                ) {
                    navHostController.navigate(
                        route = PetDetailsRoute(
                            catModel = it
                        )
                    )
                }
            }

            composable<PetDetailsRoute>(
                typeMap = mapOf(typeOf<CatModel>() to CatType)
            ) { backStackEntry ->
                val detailsParameters = backStackEntry.toRoute<PetDetailsRoute>()
                PetDetailsScreen(
                    animatedVisibilityScope = this,
                    catModel = detailsParameters.catModel,
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }

            composable<FavoritesRoute> {
                FavoritesScreen()
            }
        }
    }
}

sealed interface NavigationType {
    data object BottomNavigation : NavigationType
    data object NavigationDrawer : NavigationType
    data object NavigationRail : NavigationType
}

sealed interface ContentType {
    data object List : ContentType
    data object Details : ContentType
}