package com.kakapo.data.repository.base

import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SystemRepository {
    @NativeCoroutines
    suspend fun saveTheme(mode: Theme): Result<Unit>
    @NativeCoroutines
    suspend fun loadSavedTheme(): Result<Theme>
    @NativeCoroutines
    suspend fun saveCurrency(currency: Currency): Result<Unit>
    @NativeCoroutines
    suspend fun loadSavedCurrency(): Result<Currency>
}