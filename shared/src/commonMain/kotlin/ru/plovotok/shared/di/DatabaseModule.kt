package ru.plovotok.shared.di

import org.koin.dsl.module
import ru.plovotok.shared.db.AppDatabase
import ru.plovotok.shared.db.AppDatabaseConstructor

internal val dataBaseModule = module {
    single<AppDatabase> { AppDatabaseConstructor.initialize() }
}