package com.example.testkmpapp.di

import androidx.room.RoomDatabase
import com.example.testkmpapp.IOSPlatform
import com.example.testkmpapp.Platform
import com.example.testkmpapp.db.AppDatabase
import com.example.testkmpapp.data.db.getDatabaseBuilder
import com.example.testkmpapp.db.getRoomDatabase
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