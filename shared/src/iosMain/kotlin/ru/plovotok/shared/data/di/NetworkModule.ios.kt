package ru.plovotok.shared.data.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

internal actual fun getEngine(): HttpClientEngineFactory<*> = Darwin