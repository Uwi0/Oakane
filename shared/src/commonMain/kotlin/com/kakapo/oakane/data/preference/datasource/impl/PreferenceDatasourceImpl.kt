package com.kakapo.oakane.data.preference.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource
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

    override suspend fun getLongValue(key: String): Long {
        val preferences = dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: DEFAULT_LONG_VALUE
        }

        return preferences.first()
    }

    companion object {
        private const val DEFAULT_LONG_VALUE: Long = 0
    }
}