package com.kakapo.database.datasource.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GetWallets
import com.kakapo.WalletTable
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.database.model.WalletEntity
import com.kakapo.database.model.toWalletEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletLocalDatasourceImpl(
    driver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
) : WalletLocalDatasource {

    private val walletTable = Database.invoke(driver).walletEntityQueries

    override suspend fun createDefaultWallet(): Result<Unit> {
        return runCatching { walletTable.createDefaultWallet() }
    }

    override suspend fun update(
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

    override suspend fun getWalletBy(id: Long): Result<WalletEntity> {
        return runCatching { walletTable.getWalletBy(id).executeAsOne().toWalletEntity() }
    }

    override suspend fun insert(wallet: WalletEntity): Result<Unit> {
        return runCatching {
            walletTable.insertWallet(
                name = wallet.name,
                balance = wallet.balance,
                color = wallet.color,
                icon = wallet.icon,
                isDefaultIcon = wallet.isDefaultIcon,
                note = wallet.note,
            )
        }
    }

    override fun getWallets(): Flow<Result<List<WalletEntity>>> {
        return walletTable.getWallets().asFlow().mapToList(dispatcher).map { wallets ->
            runCatching { wallets.map(GetWallets::toWalletEntity) }
        }
    }

    override suspend fun update(wallet: WalletEntity): Result<Unit> {
        return runCatching {
            walletTable.updateWallet(
                name = wallet.name,
                balance = wallet.balance,
                color = wallet.color,
                icon = wallet.icon,
                isDefaultIcon = wallet.isDefaultIcon,
                updateAt = wallet.updateAt,
                note = wallet.note,
                id = wallet.id
            )
        }
    }

    override suspend fun deleteWalletBy(id: Long): Result<Unit> {
        return runCatching { walletTable.deleteWallet(id) }
    }

    override suspend fun geTotalBalance(): Result<Double> {
        return runCatching { walletTable.getTotalBalance().executeAsOne() }
    }

    override suspend fun getWalletForBackup(): Result<List<WalletEntity>> {
        return runCatching {
            walletTable.getWalletsForBackup()
                .executeAsList()
                .map(WalletTable::toWalletEntity)
        }
    }

    override suspend fun restoreWallets(wallets: List<WalletEntity>): Result<Unit> {
        return runCatching {
            wallets.forEach {
                walletTable.insertBakupWallets(
                    id = it.id,
                    name = it.name,
                    balance = it.balance,
                    color = it.color,
                    icon = it.icon,
                    isDefaultIcon = it.isDefaultIcon,
                    createdAt = it.createdAt,
                    updateAt = it.updateAt
                )
            }
        }
    }
}