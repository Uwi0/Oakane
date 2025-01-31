package com.kakapo.domain.usecase.impl

import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletTransferRepository
import com.kakapo.domain.usecase.base.WalletLogItemsUseCase
import com.kakapo.model.wallet.WalletLogItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

typealias WalletCollector = FlowCollector<Result<List<WalletLogItem<*>>>>

class WalletLogItemsUseCaseImpl(
    private val walletTransferRepository: WalletTransferRepository,
    private val transactionRepository: TransactionRepository,
    private val dispatcher: CoroutineDispatcher
) : WalletLogItemsUseCase {

    override fun execute(walletId: Long): Flow<Result<List<WalletLogItem<*>>>> = flow {
        try {
            combineFlowResult(walletId)
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

    }.flowOn(dispatcher)

    private suspend fun WalletCollector.combineFlowResult(walletId: Long) = coroutineScope {
        val transferLogDeferred = async {
            mapWalletTransferWith(walletId)
        }

        val transactionLogDeferred = async {
            mapTransactionWith(walletId)
        }

        supervisorScope {
            val transferLog = transferLogDeferred.await()
            val transactionLog = transactionLogDeferred.await()

            emit(Result.success(buildList {
                transferLog.onSuccess { addAll(it) }
                transactionLog.onSuccess { addAll(it) }
            }))
        }
    }

    private suspend fun mapWalletTransferWith(walletId: Long) = walletTransferRepository
        .loadLogTransferBy(walletId)
        .map { result ->
            result.mapCatching { transfers ->
                transfers.map { WalletLogItem.WalletTransferLogItem(it) }
            }
        }
        .first()

    private suspend fun mapTransactionWith(walletId: Long) = transactionRepository
        .loadTransactionByWallet(walletId)
        .map { result ->
            result.mapCatching { transactions ->
                transactions.map { WalletLogItem.TransactionLogItem(it) }
            }
        }
        .first()
}
