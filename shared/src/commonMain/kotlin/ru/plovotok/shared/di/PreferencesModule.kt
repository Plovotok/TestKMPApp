package ru.plovotok.shared.di

import org.koin.dsl.module
import ru.plovotok.shared.data.AppPreferencesImpl
import ru.plovotok.shared.domain.AppPreferences

internal val preferencesModule = module {
    single<AppPreferences> { AppPreferencesImpl(get()) }
}