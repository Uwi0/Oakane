package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.WalletModel

interface WalletRepository {
    suspend fun loadWalletId(): Result<Long>
    suspend fun update(balance: Double): Result<Unit>
    suspend fun update(balance: Double, id: Long): Result<Unit>
    suspend fun loadWalletById(): Result<WalletModel>
}