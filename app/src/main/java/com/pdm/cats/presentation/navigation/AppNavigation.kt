package com.pdm.cats.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pdm.cats.presentation.petdetails.PetDetailsScreen
import com.pdm.cats.presentation.petlist.PetListScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.PetsScreen.route
    ) {
        composable(Screens.PetsScreen.route) {
            PetListScreen {
                navController.navigate(
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
                    navController.popBackStack()
                }
            )
        }
    }
}
