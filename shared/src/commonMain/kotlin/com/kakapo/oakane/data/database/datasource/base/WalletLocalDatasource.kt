package com.kakapo.oakane.data.database.datasource.base

interface WalletLocalDatasource {
    suspend fun updateWalletTransaction(balance: Double, updateAt: Long, walletId: Long): Result<Unit>
}