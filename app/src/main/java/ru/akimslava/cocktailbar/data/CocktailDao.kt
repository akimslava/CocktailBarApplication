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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CocktailEntity)

    @Update
    suspend fun update(item: CocktailEntity)

    @Delete
    suspend fun delete(item: CocktailEntity)

    @Query("SELECT * from cocktails WHERE id = :id")
    fun getCocktail(id: Int): Flow<CocktailEntity>

    @Query("SELECT * from cocktails")
    fun getAllCocktails(): Flow<List<CocktailEntity>>
}