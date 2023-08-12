package ru.akimslava.cocktailbar.domain

import android.net.Uri
import ru.akimslava.cocktailbar.data.CocktailEntity

data class Cocktail(
    val id: Int = 0,
    val picture: Uri? = null,
    val title: String = "",
    val description: String = "",
    val ingredients: MutableList<String> = mutableListOf(),
    val recipe: String = "",
)

fun Cocktail.toCocktailEntity(): CocktailEntity =
    CocktailEntity(
        id = this.id,
        picture = this.picture,
        title = this.title,
        description = this.description,
        ingredients = this.ingredients,
        recipe = this.recipe,
    )