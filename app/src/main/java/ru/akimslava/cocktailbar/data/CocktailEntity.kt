package ru.akimslava.cocktailbar.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.akimslava.cocktailbar.domain.Cocktail

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(Converters::class)
    val picture: Uri? = null,
    val title: String = "",
    val description: String = "",
    @TypeConverters(Converters::class)
    val ingredients: MutableList<String> = mutableListOf(),
    val recipe: String = "",
)

fun CocktailEntity.toCocktail(): Cocktail =
    Cocktail(
        id = this.id,
        picture = this.picture,
        title = this.title,
        description = this.description,
        ingredients = this.ingredients,
        recipe = this.recipe,
    )