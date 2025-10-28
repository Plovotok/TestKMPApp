package com.example.testkmpapp.di

import androidx.room.RoomDatabase
import com.example.testkmpapp.AndroidPlatform
import com.example.testkmpapp.Platform
import com.example.testkmpapp.db.AppDatabase
import com.example.testkmpapp.data.db.getDatabaseBuilder
import com.example.testkmpapp.db.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Platform> { AndroidPlatform() }
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder(get()) }
    single<AppDatabase> { getRoomDatabase(get()) }
}