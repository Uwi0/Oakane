package com.kakapo.oakane.data.preference.datasource.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface PreferenceDatasource {
    @NativeCoroutines
    suspend fun saveLongValue(key: String, value: Long)
    @NativeCoroutines
    suspend fun getLongValue(key: String): Long
}