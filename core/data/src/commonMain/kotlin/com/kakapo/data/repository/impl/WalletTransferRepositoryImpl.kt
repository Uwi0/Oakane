package com.kakapo.data.repository.impl

import com.kakapo.data.model.WalletTransferParam
import com.kakapo.data.model.toWalletTransferModel
import com.kakapo.data.repository.base.WalletTransferRepository
import com.kakapo.database.datasource.base.WalletTransferLocalDatasource
import com.kakapo.model.asCurrency
import com.kakapo.model.wallet.WalletTransferModel
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WalletTransferRepositoryImpl(
    private val localDatasource: WalletTransferLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource,
    private val dispatcher: CoroutineDispatcher
) : WalletTransferRepository {

    override suspend fun save(walletTransfer: WalletTransferParam): Result<Unit> {
        return localDatasource.insert(walletTransfer.toWalletTransferEntity())
    }

    override fun loadLogTransferBy(walletId: Long): Flow<Result<List<WalletTransferModel>>> = flow {
        emit(preferenceDatasource.getSavedCurrency().asCurrency())
    }.flatMapLatest { currency ->
        localDatasource.getWalletTransferBy(walletId).map { results ->
            results.mapCatching { transactions ->
                transactions.map { it.toWalletTransferModel(currency) }
            }
        }
    }.flowOn(dispatcher)
}