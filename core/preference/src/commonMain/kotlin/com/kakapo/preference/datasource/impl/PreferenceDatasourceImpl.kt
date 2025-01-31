package com.kakapo.preference.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceDatasourceImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceDatasource {
    override suspend fun saveLongValue(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    override suspend fun saveBooleanValue(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun saveIntValue(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override suspend fun saveStringValue(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getLongValue(key: String): Long {
        val preferences = dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: DEFAULT_LONG_VALUE
        }

        return preferences.first()
    }

    override suspend fun getBooleanValue(key: String): Boolean {
        val preferences = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: DEFAULT_BOOLEAN_VALUE
        }

        return preferences.first()
    }

    override suspend fun getIntValue(key: String): Int {
        val preferences = dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: DEFAULT_INT_VALUE
        }

        return preferences.first()
    }

    override suspend fun getStringValue(key: String): String {
        val preferences = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: DEFAULT_STRING_VALUE
        }

        return preferences.first()
    }

    companion object {
        private const val DEFAULT_LONG_VALUE: Long = 0
        private const val DEFAULT_BOOLEAN_VALUE: Boolean = false
        private const val DEFAULT_INT_VALUE: Int = 0
        private const val DEFAULT_STRING_VALUE: String = ""
    }
}