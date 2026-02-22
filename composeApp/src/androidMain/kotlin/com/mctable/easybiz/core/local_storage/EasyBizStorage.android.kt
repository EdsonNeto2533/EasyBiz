package com.mctable.easybiz.core.local_storage
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "easybiz_prefs")

internal class AndroidEasyBizStorage(private val context: Context) : EasyBizStorage {
    override suspend fun setString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val prefKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[prefKey]
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun setInt(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun getInt(key: String): Int? {
        return try {
            val prefKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[prefKey]
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return try {
            val prefKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[prefKey]
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun setFloat(key: String, value: Float) {
        val prefKey = floatPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun getFloat(key: String): Float? {
        return try {
            val prefKey = floatPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[prefKey]
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun setLong(key: String, value: Long) {
        val prefKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun getLong(key: String): Long? {
        return try {
            val prefKey = longPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[prefKey]
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun setDouble(key: String, value: Double) {
        val prefKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun getDouble(key: String): Double? {
        return try {
            val prefKey = doublePreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[prefKey]
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun removeValue(key: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences.remove(prefKey)
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
