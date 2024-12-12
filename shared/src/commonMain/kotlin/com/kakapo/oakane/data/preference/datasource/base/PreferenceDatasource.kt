package com.kakapo.oakane.data.preference.datasource.base

interface PreferenceDatasource {
    suspend fun saveLongValue(key: String, value: Long)
    suspend fun getLongValue(key: String): Long
}