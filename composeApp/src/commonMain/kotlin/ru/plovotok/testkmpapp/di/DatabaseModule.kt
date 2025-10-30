package ru.plovotok.testkmpapp.di

import ru.plovotok.testkmpapp.db.AppDatabase
import ru.plovotok.testkmpapp.db.AppDatabaseConstructor
import org.koin.dsl.module

internal val dataBaseModule = module {
    single<AppDatabase> { AppDatabaseConstructor.initialize() }
}