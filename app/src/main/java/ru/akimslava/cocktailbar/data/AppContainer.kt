package ru.akimslava.cocktailbar.data

import android.content.Context

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