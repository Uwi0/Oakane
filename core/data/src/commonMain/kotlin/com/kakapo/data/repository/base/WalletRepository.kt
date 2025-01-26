package com.kakapo.data.repository.base

import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    @NativeCoroutines
    suspend fun createDefaultWallet(): Result<Unit>
    @NativeCoroutines
    suspend fun saveWallet(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun loadSelectedWallet(): Result<WalletModel>
    @NativeCoroutines
    suspend fun update(balance: Double, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun loadWalletBy(id: Long): Result<WalletModel>
    @NativeCoroutines
    suspend fun save(wallet: WalletModel): Result<Unit>
    @NativeCoroutines
    fun loadWalletItems(): Flow<Result<List<WalletItemModel>>>
    @NativeCoroutines
    fun loadWallets(): Flow<Result<List<WalletModel>>>
    @NativeCoroutines
    suspend fun update(wallet: WalletModel): Result<Unit>
    @NativeCoroutines
    suspend fun deleteWalletBy(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun loadTotalBalance(): Result<Double>
}