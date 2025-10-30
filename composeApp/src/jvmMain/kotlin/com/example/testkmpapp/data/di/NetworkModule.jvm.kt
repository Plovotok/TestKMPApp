package com.example.testkmpapp.data.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.apache5.Apache5

actual fun getEngine(): HttpClientEngineFactory<*> = Apache5