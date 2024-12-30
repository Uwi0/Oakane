package com.kakapo.oakane.data.preference.datasource.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface PreferenceDatasource {
    @NativeCoroutines
    suspend fun saveLongValue(key: String, value: Long)
    @NativeCoroutines
    suspend fun saveBooleanValue(key: String, value: Boolean)
    @NativeCoroutines
    suspend fun saveIntValue(key: String, value: Int)
    @NativeCoroutines
    suspend fun getLongValue(key: String): Long
    @NativeCoroutines
    suspend fun getBooleanValue(key: String): Boolean
    @NativeCoroutines
    suspend fun getIntValue(key: String): Int
}