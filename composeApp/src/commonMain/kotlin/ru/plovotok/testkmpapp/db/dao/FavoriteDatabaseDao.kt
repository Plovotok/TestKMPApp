package ru.plovotok.testkmpapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.plovotok.testkmpapp.db.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDatabaseDao {

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(entity: FavoriteBookEntity)

    @Query("DELETE from favorites WHERE remoteId = :remoteId")
    suspend fun removeFromFavorite(remoteId: Int)
}