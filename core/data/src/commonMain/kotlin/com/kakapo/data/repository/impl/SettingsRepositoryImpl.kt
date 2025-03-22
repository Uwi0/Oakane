package com.kakapo.data.repository.impl

import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.preference.constant.BooleanKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.isBalanceVisible

class SettingsRepositoryImpl(
    private val preferenceDatasource: PreferenceDatasource
): SettingsRepository {

    override suspend fun saveOnBoardingAlreadyRead(): Result<Unit> = runCatching {
        preferenceDatasource.saveBooleanValue(BooleanKey.ON_BOARDING_ALREADY_READ, true)
    }

    override suspend fun loadOnBoardingAlreadyRead(): Result<Boolean> = runCatching {
        preferenceDatasource.getBooleanValue(BooleanKey.ON_BOARDING_ALREADY_READ)
    }

    override suspend fun changeBalanceVisibility(visibility: Boolean): Result<Boolean> = runCatching {
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