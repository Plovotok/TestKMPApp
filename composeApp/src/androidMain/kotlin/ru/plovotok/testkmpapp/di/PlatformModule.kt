package ru.plovotok.testkmpapp.di

import androidx.room.RoomDatabase
import ru.plovotok.testkmpapp.AndroidPlatform
import ru.plovotok.testkmpapp.Platform
import ru.plovotok.testkmpapp.db.AppDatabase
import ru.plovotok.testkmpapp.data.db.getDatabaseBuilder
import ru.plovotok.testkmpapp.db.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Platform> { AndroidPlatform() }
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder(get() ) }
    single<AppDatabase> { getRoomDatabase(get()) }
}