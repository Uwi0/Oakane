package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GetWallets
import com.kakapo.oakane.data.database.datasource.base.WalletLocalDatasource
import com.kakapo.oakane.data.database.model.WalletEntity
import com.kakapo.oakane.data.database.model.toWalletEntity

class WalletLocalDatasourceImpl(
    driver: SqlDriver
) : WalletLocalDatasource {

    private val walletTable = Database.invoke(driver).walletEntityQueries

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
                isDefaultIcon = wallet.isDefaultIcon
            )
        }
    }

    override suspend fun getWallets(): Result<List<WalletEntity>> {
        return runCatching {
            walletTable.getWallets().executeAsList().map(GetWallets::toWalletEntity)
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
            walletTable.getWallets()
                .executeAsList()
                .map(GetWallets::toWalletEntity)
        }
    }
}