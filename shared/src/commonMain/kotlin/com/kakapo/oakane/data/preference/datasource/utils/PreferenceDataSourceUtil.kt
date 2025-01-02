package com.kakapo.oakane.data.preference.datasource.utils

import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import com.kakapo.oakane.data.preference.constant.IntKey
import com.kakapo.oakane.data.preference.constant.LongKey
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

@NativeCoroutines
suspend fun PreferenceDatasource.getWalletId(): Long {
    val walletId = getLongValue(LongKey.WALLET_ID)
    return if(walletId == 0L) 1 else walletId
}

@NativeCoroutines
suspend fun PreferenceDatasource.getThemeMode(): Theme {
    val saveTheme = getIntValue(IntKey.THEME_MODE)
    return saveTheme.asTheme()
}