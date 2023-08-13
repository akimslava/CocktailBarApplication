package ru.akimslava.cocktailbar.ui.models

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.akimslava.cocktailbar.domain.Cocktail

class CocktailCreationViewModel(
    cocktail: Cocktail,
) : ViewModel() {
    val cocktail = mutableStateOf(cocktail)

    val ingredient = mutableStateOf("")
    val triedAdd = mutableStateOf(false)

    fun setPicture(newPicture: Uri?) {
        cocktail.value = cocktail.value.copy(
            picture = newPicture?.toString(),
        )
    }

    fun setIngredient(newIngredient: String) {
        ingredient.value = newIngredient
    }

    fun dropIngredient() {
        ingredient.value = ""
    }

    fun setTitle(title: String) {
        cocktail.value = cocktail.value.copy(
            title = title,
        )
    }

    fun setDescription(description: String) {
        cocktail.value = cocktail.value.copy(
            description = description,
        )
    }

    fun setRecipe(recipe: String) {
        cocktail.value = cocktail.value.copy(
            recipe = recipe,
        )
    }

    fun addIngredient(): Boolean =
        if (ingredient.value.isBlank() || ingredient.value.length > 30) {
            triedAdd.value = true
            false
        } else {
            cocktail.value.ingredients.add(ingredient.value)
            triedAdd.value = false
            true
        }

    fun removeIngredient(ingredient: String) {
        viewModelScope.launch {
            removeIngredientOnBackground(ingredient)
        }
    }

    private suspend fun removeIngredientOnBackground(ingredient: String) =
        withContext(Dispatchers.IO) {
            delay(300L)
            cocktail.value = cocktail.value.copy(
                ingredients = cocktail.value.ingredients
                    .subtract(listOf(ingredient).toSet()).toMutableList()
            )
        }

    fun isCocktailDataCorrect() =
        cocktail.value.title.isNotBlank() &&
                cocktail.value.ingredients.isNotEmpty()
}