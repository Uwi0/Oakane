package com.kakapo.data.repository.impl

import com.kakapo.data.model.WalletTransferParam
import com.kakapo.data.repository.base.WalletTransferRepository
import com.kakapo.database.datasource.base.WalletTransferLocalDatasource

class WalletTransferRepositoryImpl(
    private val walletTransferLocalDatasource: WalletTransferLocalDatasource
) : WalletTransferRepository {

    override suspend fun save(walletTransfer: WalletTransferParam): Result<Unit> {
        return walletTransferLocalDatasource.insert(walletTransfer.toWalletTransferEntity())
    }
}