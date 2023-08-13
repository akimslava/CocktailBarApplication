package ru.akimslava.cocktailbar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.akimslava.cocktailbar.CocktailBarApplication
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.models.CocktailCreationViewModel
import ru.akimslava.cocktailbar.ui.models.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                cocktailsApplication().container.cocktailsRepository,
            )
        }
    }
    @Suppress("UNCHECKED_CAST")
    class CocktailCreationFactory(
        private val cocktail: Cocktail
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CocktailCreationViewModel(cocktail) as T
    }
}

fun CreationExtras.cocktailsApplication(): CocktailBarApplication = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                as CocktailBarApplication
        )