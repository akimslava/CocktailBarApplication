package ru.akimslava.cocktailbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.akimslava.cocktailbar.ui.navigation.NavHost

@Composable
fun CocktailApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController)
}