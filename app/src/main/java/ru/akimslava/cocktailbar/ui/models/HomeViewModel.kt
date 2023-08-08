package ru.akimslava.cocktailbar.ui.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.akimslava.cocktailbar.data.Cocktail
import ru.akimslava.cocktailbar.data.CocktailsRepository

class HomeViewModel(
    cocktailsRepository: CocktailsRepository,
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        cocktailsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState(),
            )

    val currentCocktail = mutableStateOf(
        Cocktail(
            0, 0, "", "", "",
        )
    )

    fun setCurrentCocktailTitle(title: String) {
        currentCocktail.value = currentCocktail.value.copy(title = title)
    }

    fun setCurrentCocktailDescription(description: String) {
        currentCocktail.value = currentCocktail.value.copy(
            description = description,
        )
    }

    fun setCurrentCocktailRecipe(recipe: String) {
        currentCocktail.value = currentCocktail.value.copy(
            recipe = recipe,
        )
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val cocktailsList: List<Cocktail> = listOf())