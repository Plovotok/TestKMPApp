package ru.plovotok.shared.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val networkModule = module {
    single<HttpClient> { client }
}

internal expect fun getEngine(): HttpClientEngineFactory<*>

private val client by lazy {
    HttpClient(getEngine()) {
        expectSuccess = true

        install(HttpTimeout) {
            requestTimeoutMillis = 20_000
            socketTimeoutMillis = 20_000
            connectTimeoutMillis = 20_000
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    print(message)
                }

            }
            level = LogLevel.ALL
        }
    }
}