package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.WalletEntity

interface WalletLocalDatasource {
    suspend fun update(balance: Double, updateAt: Long, walletId: Long): Result<Unit>
    suspend fun getWalletBy(id: Long): Result<WalletEntity>
}