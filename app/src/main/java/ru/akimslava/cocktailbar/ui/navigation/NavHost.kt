package ru.akimslava.cocktailbar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.akimslava.cocktailbar.ui.AppViewModelProvider
import ru.akimslava.cocktailbar.ui.models.HomeViewModel
import ru.akimslava.cocktailbar.ui.screens.CocktailCreationScreen
import ru.akimslava.cocktailbar.ui.screens.CocktailInformationScreen
import ru.akimslava.cocktailbar.ui.screens.HomeScreen

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = AppViewModelProvider.Factory,
    ),
) {
    NavHost(
        navController = navController,
        startDestination = CocktailScreens.HomeScreen.name,
    ) {
        composable(route = CocktailScreens.HomeScreen.name) {
            HomeScreen(
                viewModel = viewModel,
                onButtonClick = {
                    viewModel.unselectCocktail()
                    navController.navigate(
                        route = CocktailScreens.CreationScreen.name,
                    )
                },
                onCocktailClick = {
                    viewModel.selectCocktail(it)
                    navController.navigate(
                        route = CocktailScreens.CocktailScreen.name,
                    )
                },
            )
        }
        composable(route = CocktailScreens.CreationScreen.name) {
            CocktailCreationScreen(
                viewModel = viewModel,
                onCancelClicked = {
                    viewModel.cancelUpdates()
                    navController.navigateUp()
                },
            )
        }
        composable(route = CocktailScreens.CocktailScreen.name) {
            CocktailInformationScreen(
                cocktail = viewModel.currentCocktail.value,
                onEditClick = {
                    navController.navigate(
                        route = CocktailScreens.CreationScreen.name,
                    )
                },
                onBackPressed = {
                    viewModel.unselectCocktail()
                    navController.navigateUp()
                }
            )
        }
    }
}

enum class CocktailScreens {
    HomeScreen,
    CreationScreen,
    CocktailScreen,
}