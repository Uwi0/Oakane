package com.kakapo.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.WalletTransferLocalDatasource
import com.kakapo.database.model.WalletTransferEntity

class WalletTransferLocalDatasourceImpl(sqlDriver: SqlDriver): WalletTransferLocalDatasource {

    private val walletTransferQueries = Database(sqlDriver).walletTransferEntityQueries

    override suspend fun insert(walletTransfer: WalletTransferEntity): Result<Unit> {
        return runCatching {
            walletTransferQueries.insertWalletTransfer(
                fromWalletId = walletTransfer.fromWalletId,
                toWalletId = walletTransfer.toWalletId,
                amount = walletTransfer.amount,
                notes = walletTransfer.notes,
                createdAt = walletTransfer.createdAt
            )
        }
    }
}