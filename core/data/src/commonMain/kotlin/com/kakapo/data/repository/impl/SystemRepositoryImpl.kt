package com.kakapo.data.repository.impl

import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.model.Currency
import com.kakapo.model.asCurrency
import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import com.kakapo.preference.constant.BooleanKey
import com.kakapo.preference.constant.IntKey
import com.kakapo.preference.constant.StringKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.isBalanceVisible

class SystemRepositoryImpl(
    private val preferenceDatasource: PreferenceDatasource
) : SystemRepository {

    override suspend fun saveTheme(mode: Theme): Result<Unit> = runCatching {
            preferenceDatasource.saveIntValue(IntKey.THEME_MODE, mode.ordinal)
        }

    override suspend fun loadSavedTheme(): Result<Theme> = runCatching {
        val savedTheme = preferenceDatasource.getIntValue(IntKey.THEME_MODE)
        savedTheme.asTheme()
    }

    override suspend fun saveCurrency(currency: Currency): Result<Unit> = runCatching {
        preferenceDatasource.saveIntValue(IntKey.CURRENCY, currency.ordinal)
    }

    override suspend fun loadSavedCurrency(): Result<Currency> = runCatching {
        preferenceDatasource.getIntValue(IntKey.CURRENCY).asCurrency()
    }

    override suspend fun saveOnBoardingAlreadyRead(): Result<Unit> = runCatching {
        preferenceDatasource.saveBooleanValue(BooleanKey.ON_BOARDING_ALREADY_READ, true)
    }

    override suspend fun loadOnBoardingAlreadyRead(): Result<Boolean> = runCatching {
        preferenceDatasource.getBooleanValue(BooleanKey.ON_BOARDING_ALREADY_READ)
    }

    override suspend fun changeBalance(visibility: Boolean): Result<Boolean> = runCatching {
        preferenceDatasource.saveBooleanValue(BooleanKey.IS_BALANCE_VISIBLE, visibility)
        visibility
    }

    override suspend fun isBalanceVisible(): Result<Boolean> = runCatching {
        preferenceDatasource.isBalanceVisible()
    }

    override suspend fun saveTermAndServiceCondition(): Result<Unit> = runCatching {
        preferenceDatasource.saveBooleanValue(BooleanKey.TERMS_ALREADY_READ, true)
    }

    override suspend fun loadTermAndServiceCondition(): Result<Boolean> = runCatching {
        preferenceDatasource.getBooleanValue(BooleanKey.TERMS_ALREADY_READ)
    }
}
