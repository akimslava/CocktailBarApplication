package ru.akimslava.cocktailbar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.akimslava.cocktailbar.domain.Cocktail
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
            cocktail = viewModel.currentCocktail,
            deleteCocktail = {
                viewModel.setCocktail(it)
                viewModel.deleteCocktail()
            },
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
            cocktails = viewModel.homeUiState
                .collectAsState().value.cocktailsList,
            onButtonClick = {
                navController.navigate(
                    route = "${CocktailScreens.CreationScreen.name}/0",
                )
            },
            onCocktailClick = {
                viewModel.setCocktail(it)
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
    navGraphBuilder.composable(
        route = "${CocktailScreens.CreationScreen.name}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val cocktailId = navBackStackEntry.arguments?.getInt("id")
        val cocktail = if (cocktailId == 0) {
            Cocktail()
        } else {
            viewModel.homeUiState.collectAsState()
                .value.cocktailsList.find { it.id == cocktailId }
                ?: Cocktail()
        }
        CocktailCreationScreen(
            viewModel = viewModel(
                factory = AppViewModelProvider.CocktailCreationFactory(
                    cocktail = cocktail,
                ),
            ),
            onSaveClick = {
                viewModel.setCocktail(it)
                if (it.id == 0) {
                    viewModel.saveCocktail()
                } else {
                    viewModel.updateCocktail()
                }
            },
            onCancelClick = {
                navController.navigateUp()
            },
        )
    }
}

private fun cocktailInformationScreenComposable(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    cocktail: MutableState<Cocktail>,
    deleteCocktail: (Cocktail) -> Unit,
) {
    navGraphBuilder.composable(route = CocktailScreens.CocktailScreen.name) {
        CocktailInformationScreen(
            cocktail = cocktail.value.copy(),
            onEditClick = {
                navController.navigate(
                    route = "${CocktailScreens.CreationScreen.name}/" +
                            "${cocktail.value.id}",
                )
            },
            onBackPressed = {
                navController.navigateUp()
            },
            deleteCocktail = deleteCocktail,
            navigateUp = navController::navigateUp,
        )
    }
}