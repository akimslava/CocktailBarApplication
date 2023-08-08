package ru.akimslava.cocktailbar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Cocktail)

    @Update
    suspend fun update(item: Cocktail)

    @Delete
    suspend fun delete(item: Cocktail)

    @Query("SELECT * from cocktails WHERE id = :id")
    fun getCocktail(id: Int): Flow<Cocktail>

    @Query("SELECT * from cocktails")
    fun getAllCocktails(): Flow<List<Cocktail>>
}