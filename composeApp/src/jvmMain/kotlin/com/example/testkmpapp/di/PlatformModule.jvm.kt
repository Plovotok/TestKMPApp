package com.example.testkmpapp.di

import androidx.room.RoomDatabase
import com.example.myapplication.JVMPlatform
import com.example.testkmpapp.Platform
import com.example.testkmpapp.db.AppDatabase
import com.example.testkmpapp.db.getDatabaseBuilder
import com.example.testkmpapp.db.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Platform> { JVMPlatform() }
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
    single<AppDatabase> { getRoomDatabase(get()) }
}