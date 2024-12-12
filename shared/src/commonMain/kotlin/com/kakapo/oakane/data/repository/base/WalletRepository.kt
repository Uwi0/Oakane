package com.kakapo.oakane.data.repository.base

interface WalletRepository {
    suspend fun loadWalletId(): Result<Long>
    suspend fun update(balance: Double): Result<Unit>
}