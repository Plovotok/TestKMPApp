package ru.plovotok.shared.di

import ru.plovotok.shared.data.di.networkModule
import ru.plovotok.shared.data.di.repositoryModule

fun appModule() = listOf(
    dataBaseModule,
    platformModule,
    networkModule,
    repositoryModule,
    preferencesModule
)