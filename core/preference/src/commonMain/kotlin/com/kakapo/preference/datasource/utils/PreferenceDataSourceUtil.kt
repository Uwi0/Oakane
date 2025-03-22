package com.kakapo.preference.datasource.utils

import com.kakapo.preference.constant.BooleanKey
import com.kakapo.preference.constant.IntKey
import com.kakapo.preference.constant.LongKey
import com.kakapo.preference.constant.StringKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.model.ReminderPrefs
import kotlinx.serialization.json.Json

suspend fun PreferenceDatasource.getWalletId(): Long {
    val walletId = getLongValue(LongKey.WALLET_ID)
    return if(walletId == 0L) 1 else walletId
}

suspend fun PreferenceDatasource.getSavedCurrency(): Int {
    return getIntValue(IntKey.CURRENCY)
}

suspend fun PreferenceDatasource.isBalanceVisible(): Boolean {
    return !getBooleanValue(BooleanKey.IS_BALANCE_VISIBLE)
}

suspend fun PreferenceDatasource.save(reminder: ReminderPrefs) {
    val reminders = Json.encodeToString(reminder.reminders)
    saveBooleanValue(BooleanKey.IS_REMINDER_ENABLED, reminder.isReminderEnabled)
    saveIntValue(IntKey.REMINDER_HOUR, reminder.selectedHour)
    saveIntValue(IntKey.REMINDER_MINUTE, reminder.selectedMinute)
    saveStringValue(StringKey.REMINDER_DAYS, reminders)
}

suspend fun PreferenceDatasource.getReminderPrefs(): ReminderPrefs {
    val isReminderEnabled = getBooleanValue(BooleanKey.IS_REMINDER_ENABLED)
    val selectedHour = getIntValue(IntKey.REMINDER_HOUR)
    val selectedMinute = getIntValue(IntKey.REMINDER_MINUTE)
    val jsonReminders = getStringValue(StringKey.REMINDER_DAYS)
    val reminders = Json.decodeFromString<List<String>>(jsonReminders)
    return ReminderPrefs(reminders, isReminderEnabled, selectedHour, selectedMinute)
}