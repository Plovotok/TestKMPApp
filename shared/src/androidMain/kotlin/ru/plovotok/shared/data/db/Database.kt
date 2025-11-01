package ru.plovotok.shared.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.plovotok.shared.db.AppDatabase
import ru.plovotok.shared.db.DATABASE_NAME

internal fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}