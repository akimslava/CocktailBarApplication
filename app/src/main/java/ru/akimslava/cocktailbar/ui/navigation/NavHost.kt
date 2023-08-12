package ru.akimslava.cocktailbar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.akimslava.cocktailbar.ui.AppViewModelProvider
import ru.akimslava.cocktailbar.ui.models.HomeViewModel
import ru.akimslava.cocktailbar.ui.screens.CocktailCreationScreen
import ru.akimslava.cocktailbar.ui.screens.CocktailInformationScreen
import ru.akimslava.cocktailbar.ui.screens.HomeScreen

enum class CocktailScreens {
    HomeScreen,
    CreationScreen,
    CocktailScreen,
}

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
        homeScreenComposable(
            navGraphBuilder = this,
            navController = navController,
            viewModel = viewModel,
        )
        creationScreenComposable(
            navGraphBuilder = this,
            navController = navController,
            viewModel = viewModel,
        )
        cocktailInformationScreenComposable(
            navGraphBuilder = this,
            navController = navController,
            viewModel = viewModel,
        )
    }
}

private fun homeScreenComposable(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    navGraphBuilder.composable(route = CocktailScreens.HomeScreen.name) {
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
}

private fun creationScreenComposable(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    navGraphBuilder.composable(route = CocktailScreens.CreationScreen.name) {
        CocktailCreationScreen(
            viewModel = viewModel,
            onCancelClicked = {
                viewModel.cancelUpdates()
                navController.navigateUp()
            },
        )
    }
}

private fun cocktailInformationScreenComposable(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    navGraphBuilder.composable(route = CocktailScreens.CocktailScreen.name) {
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
            },
            deleteCocktail = { viewModel.deleteCocktail() },
            navigateUp = navController::navigateUp,
        )
    }
}