package ru.plovotok.shared.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.plovotok.shared.data.db.getDatabaseBuilder
import ru.plovotok.shared.db.AppDatabase
import ru.plovotok.shared.db.getRoomDatabase
import ru.plovotok.shared.domain.createDataStoreIos

internal actual val platformModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
    single<AppDatabase> { getRoomDatabase(get()) }
    single<DataStore<Preferences>> { createDataStoreIos() }
}