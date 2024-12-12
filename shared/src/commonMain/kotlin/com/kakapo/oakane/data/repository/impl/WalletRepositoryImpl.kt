package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.WalletLocalDatasource
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource
import com.kakapo.oakane.data.preference.datasource.utils.getWalletId
import com.kakapo.oakane.data.repository.base.WalletRepository
import kotlinx.datetime.Clock

class WalletRepositoryImpl(
    private val localDatasource: WalletLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource
) : WalletRepository {

    override suspend fun loadWalletId(): Result<Long> {
        return runCatching { preferenceDatasource.getWalletId() }
    }

    override suspend fun update(balance: Double): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val walletId = preferenceDatasource.getWalletId()
        return localDatasource.update(balance, currentTime, walletId)
    }

    override suspend fun update(balance: Double, id: Long): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.update(balance, currentTime, id)
    }
}