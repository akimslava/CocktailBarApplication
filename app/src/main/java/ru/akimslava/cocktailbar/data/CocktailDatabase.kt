package ru.akimslava.cocktailbar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Cocktail::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun getDao(): CocktailDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailDatabase? = null

        fun getDatabase(context: Context): CocktailDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    CocktailDatabase::class.java,
                    "cocktails_database",
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}