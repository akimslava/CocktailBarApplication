package ru.akimslava.cocktailbar.ui.models

import androidx.compose.runtime.MutableState
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
    private val cocktail = mutableStateOf(cocktail)

    private val ingredient = mutableStateOf("")
    private val triedAddCocktail = mutableStateOf(false)
    private val triedAddIngredient = mutableStateOf(false)

    fun getCocktail(): Cocktail = cocktail.value

    fun getCocktailState(): MutableState<Cocktail> = cocktail

    fun setPicture(newPicture: String?) {
        cocktail.value = cocktail.value.copy(
            picture = newPicture,
        )
    }

    fun tryAddCocktail() {
        triedAddCocktail.value = true
    }

    fun successfulAddingCocktail() {
        triedAddCocktail.value = false
    }

    fun isTitleIncorrect(): Boolean =
        triedAddCocktail.value && cocktail.value.title.isBlank()

    fun getIngredient(): String = ingredient.value

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

    fun isIngredientsIncorrect(): Boolean =
            triedAddCocktail.value && cocktail.value.ingredients.isEmpty()

    fun setRecipe(recipe: String) {
        cocktail.value = cocktail.value.copy(
            recipe = recipe,
        )
    }

    fun addIngredient(): Boolean =
        if (isIngredientValid()) {
            triedAddIngredient.value = true
            false
        } else {
            val newIngredients = cocktail.value.ingredients.toMutableList()
            newIngredients.add(ingredient.value)
            cocktail.value = cocktail.value.copy(
                 ingredients = newIngredients,
            )
            triedAddIngredient.value = false
            true
        }

    fun isIngredientValid(): Boolean =
        ingredient.value.isBlank() ||
                ingredient.value.length > 30

    fun haveTriedAddIngredient(): Boolean = triedAddIngredient.value

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
                    .subtract(setOf(ingredient)).toMutableList()
            )
        }

    fun isCocktailDataCorrect() =
        cocktail.value.title.isNotBlank() &&
                cocktail.value.ingredients.isNotEmpty()
}