package ru.akimslava.cocktailbar.ui.models

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.akimslava.cocktailbar.data.Cocktail
import ru.akimslava.cocktailbar.data.CocktailsRepository

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
    private val cocktailCopy = mutableStateOf(Cocktail())
    var isCocktailSelected = false
        private set

    val ingredient = mutableStateOf("")

    fun setUri(uri: Uri) {
        currentCocktail.value = currentCocktail.value.copy(
            picture = uri,
        )
    }

    fun setIngredient(newIngredient: String) {
        ingredient.value = newIngredient
    }

    fun dropIngredient() {
        ingredient.value = ""
    }
    fun selectCocktail(cocktail: Cocktail) {
        currentCocktail.value = cocktail
        cocktailCopy.value = cocktail
        isCocktailSelected = true
    }

    fun unselectCocktail() {
        isCocktailSelected = false
    }

    fun dropCurrentCocktail() {
        currentCocktail.value = Cocktail()
    }

    fun cancelUpdates() {
        currentCocktail.value = cocktailCopy.value
    }

    fun setTitle(title: String) {
        currentCocktail.value = currentCocktail.value.copy(
            title = title,
        )
    }

    fun setDescription(description: String) {
        currentCocktail.value = currentCocktail.value.copy(
            description = description,
        )
    }

    fun setRecipe(recipe: String) {
        currentCocktail.value = currentCocktail.value.copy(
            recipe = recipe,
        )
    }

    fun addIngredient(): Boolean =
        if (ingredient.value.isBlank() || ingredient.value.length > 30) {
            false
        } else {
            currentCocktail.value.ingredients.add(ingredient.value)
            true
        }

    fun removeIngredient(ingredient: String) {
        currentCocktail.value.ingredients.remove(ingredient)
    }

    fun saveCocktail() {
        viewModelScope.launch {
            cocktailsRepository.insertItem(currentCocktail.value)
        }
    }

    fun isCocktailDataCorrect() =
        currentCocktail.value.title.isNotBlank() &&
                currentCocktail.value.ingredients.isNotEmpty()

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val cocktailsList: List<Cocktail> = listOf())