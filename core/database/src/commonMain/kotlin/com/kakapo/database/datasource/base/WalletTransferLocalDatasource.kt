package com.kakapo.database.datasource.base

import com.kakapo.database.model.WalletTransferEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface WalletTransferLocalDatasource {
    @NativeCoroutines
    suspend fun insert(walletTransfer: WalletTransferEntity): Result<Unit>
}