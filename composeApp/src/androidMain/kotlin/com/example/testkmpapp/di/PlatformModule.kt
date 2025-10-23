package com.example.testkmpapp.di

import com.example.testkmpapp.AndroidPlatform
import com.example.testkmpapp.Platform
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Platform> { AndroidPlatform() }
}