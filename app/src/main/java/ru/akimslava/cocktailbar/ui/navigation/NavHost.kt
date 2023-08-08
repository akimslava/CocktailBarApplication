package ru.akimslava.cocktailbar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.akimslava.cocktailbar.ui.screens.CocktailCreationScreen
import ru.akimslava.cocktailbar.ui.screens.HomeScreen

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = CocktailScreens.HomeScreen.name,
    ) {
        composable(route = CocktailScreens.HomeScreen.name) {
            HomeScreen(
                onButtonClick = {
                    navController.navigate(
                        route = CocktailScreens.CreationScreen.name,
                    )
                },
            )
        }
        composable(route = CocktailScreens.CreationScreen.name) {
            CocktailCreationScreen()
        }
    }
}

enum class CocktailScreens {
    HomeScreen,
    CreationScreen,
}