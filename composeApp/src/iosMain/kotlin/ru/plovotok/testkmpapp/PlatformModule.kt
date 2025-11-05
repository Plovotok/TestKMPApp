package ru.plovotok.testkmpapp

import org.koin.core.context.startKoin
import ru.plovotok.shared.di.appModule
import ru.plovotok.testkmpapp.presentation.di.viewModelsModule

fun initKoin() {
    startKoin {
        modules(viewModelsModule)
        modules(appModule())
    }
}