package com.kakapo.database.datasource.base

import com.kakapo.database.model.WalletEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface WalletLocalDatasource {
    @NativeCoroutines
    suspend fun createDefaultWallet(): Result<Unit>
    @NativeCoroutines
    suspend fun update(balance: Double, updateAt: Long, walletId: Long): Result<Unit>
    @NativeCoroutines
    suspend fun getWalletBy(id: Long): Result<WalletEntity>
    @NativeCoroutines
    suspend fun insert(wallet: WalletEntity): Result<Unit>
    @NativeCoroutines
    suspend fun getWallets(): Result<List<WalletEntity>>
    @NativeCoroutines
    suspend fun update(wallet: WalletEntity): Result<Unit>
    @NativeCoroutines
    suspend fun deleteWalletBy(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun geTotalBalance(): Result<Double>
    @NativeCoroutines
    suspend fun getWalletForBackup(): Result<List<WalletEntity>>
    @NativeCoroutines
    suspend fun restoreWallets(wallets: List<WalletEntity>): Result<Unit>
}