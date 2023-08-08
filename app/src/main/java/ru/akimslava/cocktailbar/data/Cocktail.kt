package ru.akimslava.cocktailbar.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "cocktails")
data class Cocktail(
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