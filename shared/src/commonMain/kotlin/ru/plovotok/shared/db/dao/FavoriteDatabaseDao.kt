package ru.plovotok.shared.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.plovotok.shared.db.entity.FavoriteBookEntity

@Dao
internal interface FavoriteDatabaseDao {

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(entity: FavoriteBookEntity)

    @Query("DELETE from favorites WHERE remoteId = :remoteId")
    suspend fun removeFromFavorite(remoteId: Int)
}