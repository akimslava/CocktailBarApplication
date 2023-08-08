package ru.akimslava.cocktailbar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class Cocktail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val picture: Int,
    val title: String,
    val description: String,
    val recipe: String,
)