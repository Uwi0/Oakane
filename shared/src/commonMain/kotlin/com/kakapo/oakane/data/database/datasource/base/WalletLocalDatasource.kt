package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.WalletEntity

interface WalletLocalDatasource {
    suspend fun update(balance: Double, updateAt: Long, walletId: Long): Result<Unit>
    suspend fun getWalletBy(id: Long): Result<WalletEntity>
    suspend fun insert(wallet: WalletEntity): Result<Unit>
    suspend fun getWallets(): Result<List<WalletEntity>>
    suspend fun update(wallet: WalletEntity): Result<Unit>
    suspend fun deleteWalletBy(id: Long): Result<Unit>
}