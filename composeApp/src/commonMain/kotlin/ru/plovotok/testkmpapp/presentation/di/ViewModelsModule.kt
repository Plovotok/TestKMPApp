package ru.plovotok.testkmpapp.presentation.di

import org.koin.dsl.module
import ru.plovotok.testkmpapp.presentation.ViewModelFactory

val viewModelsModule = module {
    single<ViewModelFactory> { ViewModelFactory() }
}