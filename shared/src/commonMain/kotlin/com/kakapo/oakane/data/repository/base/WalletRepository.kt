package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.model.wallet.WalletModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    @NativeCoroutines
    suspend fun saveWallet(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun loadWalletId(): Result<Long>
    @NativeCoroutines
    suspend fun update(balance: Double): Result<Unit>
    @NativeCoroutines
    suspend fun update(balance: Double, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun loadWalletById(): Result<WalletModel>
    @NativeCoroutines
    suspend fun save(wallet: WalletModel): Result<Unit>
    @NativeCoroutines
    fun loadWallets(): Flow<Result<List<WalletItemModel>>>
    @NativeCoroutines
    suspend fun update(wallet: WalletModel): Result<Unit>
    @NativeCoroutines
    suspend fun deleteWalletBy(id: Long): Result<Unit>
}