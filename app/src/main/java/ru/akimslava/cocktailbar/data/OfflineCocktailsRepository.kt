package ru.akimslava.cocktailbar.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.domain.CocktailsRepository
import ru.akimslava.cocktailbar.domain.toCocktailEntity

class OfflineCocktailsRepository(
    private val cocktailDao: CocktailDao,
) : CocktailsRepository {
    override fun getAllItemsStream(): Flow<List<Cocktail>> =
        cocktailDao.getAllCocktails().map { cocktailEntityList ->
            cocktailEntityList.map { cocktailEntity ->
                cocktailEntity.toCocktail()
            }
        }

    override fun getItemStream(id: Int): Flow<Cocktail> =
        cocktailDao.getCocktail(id).map { it.toCocktail() }

    override suspend fun insertItem(item: Cocktail) =
        cocktailDao.insert(item.toCocktailEntity())

    override suspend fun updateItem(item: Cocktail) =
        cocktailDao.update(item.toCocktailEntity())

    override suspend fun deleteItem(item: Cocktail) =
        cocktailDao.delete(item.toCocktailEntity())

    override suspend fun hasSameImage(item: Cocktail): Boolean =
        cocktailDao.getCocktailsWithSamePicture(item.picture).size > 1
}