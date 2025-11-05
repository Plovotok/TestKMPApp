package ru.plovotok.testkmpapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.plovotok.shared.di.appModule
import ru.plovotok.testkmpapp.presentation.di.viewModelsModule

class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TestApp)
            androidLogger()
            modules(appModule())
            modules(viewModelsModule)
        }
    }
}