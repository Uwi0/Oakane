package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.model.wallet.WalletModel
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun loadWalletId(): Result<Long>
    suspend fun update(balance: Double): Result<Unit>
    suspend fun update(balance: Double, id: Long): Result<Unit>
    suspend fun loadWalletById(): Result<WalletModel>
    suspend fun save(wallet: WalletModel): Result<Unit>
    fun loadWallets(): Flow<Result<List<WalletItemModel>>>
}