package ru.plovotok.testkmpapp

import org.koin.core.context.startKoin
import ru.plovotok.shared.di.appModule

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}