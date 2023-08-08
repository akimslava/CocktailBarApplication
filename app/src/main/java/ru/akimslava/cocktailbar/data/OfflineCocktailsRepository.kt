package ru.akimslava.cocktailbar.data

import kotlinx.coroutines.flow.Flow

class OfflineCocktailsRepository(
    private val cocktailDao: CocktailDao,
) : CocktailsRepository {
    override fun getAllItemsStream(): Flow<List<Cocktail>> = cocktailDao.getAllCocktails()

    override fun getItemStream(id: Int): Flow<Cocktail> = cocktailDao.getCocktail(id)

    override suspend fun insertItem(item: Cocktail) = cocktailDao.insert(item)

    override suspend fun updateItem(item: Cocktail) = cocktailDao.update(item)

    override suspend fun deleteItem(item: Cocktail) = cocktailDao.delete(item)
}