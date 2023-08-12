package ru.akimslava.cocktailbar.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.akimslava.cocktailbar.CocktailBarApplication
import ru.akimslava.cocktailbar.ui.models.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                cocktailsApplication().container.cocktailsRepository,
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.cocktailsApplication(): CocktailBarApplication = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                as CocktailBarApplication
        )