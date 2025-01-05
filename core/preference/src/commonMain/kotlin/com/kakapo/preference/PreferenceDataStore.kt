package com.kakapo.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<Preferences>{
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )
}

internal const val dataStoreFileName = "oakane.preferences_pb"

expect class OakanePreferenceDataStoreFactory {
    fun dataStore(): DataStore<Preferences>
}