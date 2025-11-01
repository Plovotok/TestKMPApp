package ru.plovotok.shared.di

import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.plovotok.shared.data.db.getDatabaseBuilder
import ru.plovotok.shared.db.AppDatabase
import ru.plovotok.shared.db.getRoomDatabase

internal actual val platformModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder(get() ) }
    single<AppDatabase> { getRoomDatabase(get()) }
}