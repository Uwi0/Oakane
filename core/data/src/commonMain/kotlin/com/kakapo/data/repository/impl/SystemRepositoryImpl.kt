package com.kakapo.data.repository.impl

import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.model.Currency
import com.kakapo.model.asCurrency
import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import com.kakapo.preference.constant.IntKey
import com.kakapo.preference.datasource.base.PreferenceDatasource

class SystemRepositoryImpl(
    private val preferenceDatasource: PreferenceDatasource
) : SystemRepository {

    override suspend fun saveTheme(mode: Theme): Result<Unit> {
        return runCatching {
            preferenceDatasource.saveIntValue(IntKey.THEME_MODE, mode.ordinal)
        }
    }

    override suspend fun loadSavedTheme(): Result<Theme> {
        return runCatching {
            val savedTheme = preferenceDatasource.getIntValue(IntKey.THEME_MODE)
            savedTheme.asTheme()
        }
    }

    override suspend fun saveCurrency(currency: Currency): Result<Unit> {
        return runCatching {
            preferenceDatasource.saveIntValue(IntKey.CURRENCY, currency.ordinal)
        }
    }

    override suspend fun loadSavedCurrency(): Result<Currency> {
        return runCatching {
            preferenceDatasource.getIntValue(IntKey.CURRENCY).asCurrency()
        }
    }
}
