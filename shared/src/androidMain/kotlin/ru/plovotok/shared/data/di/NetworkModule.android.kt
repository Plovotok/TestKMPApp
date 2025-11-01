package ru.plovotok.shared.data.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android

internal actual fun getEngine(): HttpClientEngineFactory<*> = Android