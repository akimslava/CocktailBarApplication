package ru.akimslava.cocktailbar.ui.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.domain.CocktailsRepository

class HomeViewModel(
    private val cocktailsRepository: CocktailsRepository,
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        cocktailsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState(),
            )

    val currentCocktail = mutableStateOf(Cocktail())

    fun setCocktail(cocktail: Cocktail) {
        currentCocktail.value = cocktail
    }

    fun saveCocktail() {
        viewModelScope.launch {
            cocktailsRepository.insertItem(currentCocktail.value)
        }
    }

    fun updateCocktail() {
        viewModelScope.launch {
            cocktailsRepository.updateItem(currentCocktail.value)
        }
    }

    fun deleteCocktail() {
        viewModelScope.launch {
            cocktailsRepository.deleteItem(currentCocktail.value)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val cocktailsList: List<Cocktail> = listOf())