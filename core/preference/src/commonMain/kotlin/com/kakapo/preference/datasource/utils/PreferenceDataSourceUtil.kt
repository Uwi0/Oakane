package com.kakapo.preference.datasource.utils

import com.kakapo.preference.constant.BooleanKey
import com.kakapo.preference.constant.IntKey
import com.kakapo.preference.constant.LongKey
import com.kakapo.preference.datasource.base.PreferenceDatasource

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