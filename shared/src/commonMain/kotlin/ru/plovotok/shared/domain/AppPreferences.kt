package ru.plovotok.shared.domain

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    fun getStringData(key: String): Flow<String?>

    suspend fun setString(key: String, value: String)

    fun getFloatData(key: String): Flow<Float?>

    suspend fun setFloat(key: String, value: Float)
}