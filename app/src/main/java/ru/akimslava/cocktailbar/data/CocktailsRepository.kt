package ru.akimslava.cocktailbar.data

import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {
    fun getAllItemsStream(): Flow<List<Cocktail>>

    fun getItemStream(id: Int): Flow<Cocktail>

    suspend fun insertItem(item: Cocktail)

    suspend fun updateItem(item: Cocktail)

    suspend fun deleteItem(item: Cocktail)
}