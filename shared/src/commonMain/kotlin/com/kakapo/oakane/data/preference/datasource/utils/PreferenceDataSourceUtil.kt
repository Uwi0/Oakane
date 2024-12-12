package com.kakapo.oakane.data.preference.datasource.utils

import com.kakapo.oakane.data.preference.constant.LongKey
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource

suspend fun PreferenceDatasource.getWalletId(): Long {
    val walletId = getLongValue(LongKey.WALLET_ID)
    return if(walletId == 0L) 1 else walletId
}