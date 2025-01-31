package com.kakapo.preference.datasource.base

interface PreferenceDatasource {
    suspend fun saveLongValue(key: String, value: Long)
    suspend fun saveBooleanValue(key: String, value: Boolean)
    suspend fun saveIntValue(key: String, value: Int)
    suspend fun saveStringValue(key: String, value: String)
    suspend fun getLongValue(key: String): Long
    suspend fun getBooleanValue(key: String): Boolean
    suspend fun getIntValue(key: String): Int
    suspend fun getStringValue(key: String): String
}