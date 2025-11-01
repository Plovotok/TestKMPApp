package ru.plovotok.shared.data.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.apache5.Apache5

internal actual fun getEngine(): HttpClientEngineFactory<*> = Apache5