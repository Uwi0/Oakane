package com.kakapo.database.datasource.base

import com.kakapo.database.model.WalletTransferEntity
import com.kakapo.database.model.WalletTransferItemEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface WalletTransferLocalDatasource {
    @NativeCoroutines
    suspend fun insert(walletTransfer: WalletTransferEntity): Result<Unit>
    @NativeCoroutines
    fun getWalletTransferBy(id: Long): Flow<Result<List<WalletTransferItemEntity>>>
    @NativeCoroutines
    fun getWalletTransfersForBackup(): Result<List<WalletTransferEntity>>
    @NativeCoroutines
    suspend fun restoreWalletTransfer(walletTransfers: List<WalletTransferEntity>): Result<Unit>
}