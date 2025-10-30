package ru.plovotok.testkmpapp.di

import androidx.room.RoomDatabase
import ru.plovotok.testkmpapp.IOSPlatform
import ru.plovotok.testkmpapp.Platform
import ru.plovotok.testkmpapp.db.AppDatabase
import ru.plovotok.testkmpapp.data.db.getDatabaseBuilder
import ru.plovotok.testkmpapp.db.getRoomDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Platform> { IOSPlatform() }
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
    single<AppDatabase> { getRoomDatabase(get()) }
}

@Suppress("unused") // Used in Swift
fun initKoin() {
    startKoin {
        modules(appModule())
    }
}