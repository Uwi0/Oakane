package com.kakapo.preference.datasource.utils

import com.kakapo.preference.constant.LongKey
import com.kakapo.preference.datasource.base.PreferenceDatasource

suspend fun PreferenceDatasource.getWalletId(): Long {
    val walletId = getLongValue(LongKey.WALLET_ID)
    return if(walletId == 0L) 1 else walletId
}