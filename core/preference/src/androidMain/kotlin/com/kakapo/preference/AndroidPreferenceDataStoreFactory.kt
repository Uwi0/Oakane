package com.kakapo.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class OakanePreferenceDataStoreFactory(private val context: Context) {
    actual fun dataStore(): DataStore<Preferences> = createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath },
    )
}