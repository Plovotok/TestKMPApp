package ru.plovotok.shared.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import ru.plovotok.shared.db.dao.FavoriteDatabaseDao
import ru.plovotok.shared.db.entity.FavoriteBookEntity

@Database(entities = [FavoriteBookEntity::class], version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class AppDatabase: RoomDatabase(), DB {
    abstract fun favoriteDao(): FavoriteDatabaseDao

    override fun clearAllTables() {}
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "KotlinNoActualForExpect")
expect internal object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal val DATABASE_NAME = "favorites.db"

interface DB {
    fun clearAllTables()
}

internal fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}