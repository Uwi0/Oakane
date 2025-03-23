package com.kakapo.data.repository.impl

import com.kakapo.data.model.toReminder
import com.kakapo.data.model.toReminderPrefs
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.model.reminder.Reminder
import com.kakapo.preference.constant.BooleanKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getReminderPrefs
import com.kakapo.preference.datasource.utils.isBalanceVisible
import com.kakapo.preference.datasource.utils.save

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

    override suspend fun saveReminder(reminder: Reminder): Result<Unit> = runCatching {
        preferenceDatasource.save(reminder.toReminderPrefs())
    }

    override suspend fun loadReminder(): Result<Reminder> = runCatching {
        preferenceDatasource.getReminderPrefs().toReminder()
    }
}