package com.example.testkmpapp.di

import com.example.testkmpapp.db.AppDatabase
import com.example.testkmpapp.db.AppDatabaseConstructor
import org.koin.dsl.module

internal val dataBaseModule = module {
    single<AppDatabase> { AppDatabaseConstructor.initialize() }
}