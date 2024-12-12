package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.oakane.data.database.datasource.base.WalletLocalDatasource

class WalletLocalDatasourceImpl(
    driver: SqlDriver
) : WalletLocalDatasource {

    private val walletTable = Database.invoke(driver).walletEntityQueries

    override suspend fun updateWalletTransaction(
        balance: Double,
        updateAt: Long,
        walletId: Long
    ): Result<Unit> = runCatching {
        walletTable.updateWalletTransaction(
            balance = balance,
            updateAt = updateAt,
            id = walletId
        )
    }
}