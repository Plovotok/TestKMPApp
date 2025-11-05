package ru.plovotok.shared.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import ru.plovotok.shared.domain.AppPreferences
import ru.plovotok.shared.domain.createDataStore

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
): AppPreferences {

    override fun getStringData(key: String): Flow<String?> = dataStore.data.map {
        it[stringPreferencesKey(key)]
    }.onEmpty { null }

    override suspend fun setString(key: String, value: String) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                this[stringPreferencesKey(key)] = value
            }
        }
    }

    override fun getFloatData(key: String): Flow<Float?> = dataStore.data.map {
        it[floatPreferencesKey(key)]
    }

    override suspend fun setFloat(key: String, value: Float) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                this[floatPreferencesKey(key)] = value
            }
        }
    }
}