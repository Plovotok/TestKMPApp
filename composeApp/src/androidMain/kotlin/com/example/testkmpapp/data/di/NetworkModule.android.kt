package com.example.testkmpapp.data.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android

actual fun getEngine(): HttpClientEngineFactory<*> = Android