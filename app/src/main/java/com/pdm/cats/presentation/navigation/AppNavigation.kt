package com.pdm.cats.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pdm.cats.presentation.favorites.FavoritesScreen
import com.pdm.cats.presentation.petdetails.PetDetailsScreen
import com.pdm.cats.presentation.petlist.PetListScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.PetsScreen.route
    ) {
        composable(Screens.PetsScreen.route) {
            PetListScreen(
                contentType = contentType,
            ) {
                navHostController.navigate(
                    "${Screens.PetDetailsScreen.route}/${Json.encodeToString(it)}"
                )
            }
        }

        composable(
            route = "${Screens.PetDetailsScreen.route}/{cat}",
            arguments = listOf(
                navArgument("cat") {
                    type = NavType.StringType
                }
            )
        )
        {
            PetDetailsScreen(
                cat = Json.decodeFromString(it.arguments?.getString("cat") ?: ""),
                onBackPressed = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(
            Screens.FavoritesScreen.route
        ) {
            FavoritesScreen()
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