package com.example.testkmpapp.di

import com.example.testkmpapp.IOSPlatform
import com.example.testkmpapp.Platform
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Platform> { IOSPlatform() }
}

@Suppress("unused") // Used in Swift
fun initKoin() {
    startKoin {
        modules(appModule())
    }
}