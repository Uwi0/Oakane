package com.kakapo.database.datasource.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GetTransfersBy
import com.kakapo.database.datasource.base.WalletTransferLocalDatasource
import com.kakapo.database.model.WalletTransferEntity
import com.kakapo.database.model.WalletTransferItemEntity
import com.kakapo.database.model.toWalletTransferEntity
import com.kakapo.database.model.toWalletTransferItemEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletTransferLocalDatasourceImpl(
    sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
) : WalletTransferLocalDatasource {

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

    override fun getWalletTransferBy(id: Long): Flow<Result<List<WalletTransferItemEntity>>> {
        return walletTransferQueries.getTransfersBy(id, id)
            .asFlow()
            .mapToList(dispatcher)
            .map { transactions ->
                runCatching { transactions.map(GetTransfersBy::toWalletTransferItemEntity) }
            }
    }

    override suspend fun getWalletTransfersForBackup(): Result<List<WalletTransferEntity>> {
        return runCatching {
            walletTransferQueries.getWalletTranfersForBackup()
                .executeAsList()
                .map { it.toWalletTransferEntity() }
        }
    }

    override suspend fun restoreWalletTransfer(walletTransfers: List<WalletTransferEntity>): Result<Unit> {
        return runCatching {
            walletTransfers.forEach { walletTransfer ->
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
}