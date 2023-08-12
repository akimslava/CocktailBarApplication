package ru.akimslava.cocktailbar.data

import android.content.Context
import ru.akimslava.cocktailbar.domain.CocktailsRepository

interface AppContainer {
    val cocktailsRepository: CocktailsRepository
}

class AppDataContainer(
    private val context: Context,
): AppContainer {
    override val cocktailsRepository: CocktailsRepository by lazy {
        OfflineCocktailsRepository(CocktailDatabase.getDatabase(context).getDao())
    }
}