package ru.akimslava.cocktailbar

import android.app.Application
import ru.akimslava.cocktailbar.data.AppContainer
import ru.akimslava.cocktailbar.data.AppDataContainer

class CocktailBarApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}