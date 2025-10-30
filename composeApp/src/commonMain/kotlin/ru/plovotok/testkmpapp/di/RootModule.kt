package ru.plovotok.testkmpapp.di

import ru.plovotok.testkmpapp.data.di.networkModule
import ru.plovotok.testkmpapp.data.di.repositoryModule

fun appModule() = listOf(
    dataBaseModule,
    platformModule,
    networkModule,
    repositoryModule
)