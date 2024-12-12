package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.model.toModel
import com.kakapo.oakane.data.preference.constant.LongKey
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionRepositoryImpl(
    private val localDatasource: TransactionLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource
) : TransactionRepository {

    override suspend fun save(transaction: TransactionParam): Result<Unit> {
        val prefWalletId = preferenceDatasource.getLongValue(LongKey.WALLET_ID)
        val walletId = if (prefWalletId == 0L) 1 else prefWalletId
        val transactionEntity = transaction.toEntity()
        return localDatasource.insertTransaction(transactionEntity.copy(walletId = walletId))
    }

    override fun loadTransactions(): Flow<Result<List<TransactionModel>>> = flow {
        val result = localDatasource.getTransactions()
            .mapCatching { it.map(TransactionEntity::toModel) }
        emit(result)
    }

    override suspend fun deleteTransactionBy(id: Long): Result<Unit> {
        return localDatasource.deleteTransaction(id)
    }

    override suspend fun loadTransactionBy(id: Long): Result<TransactionModel> {
        return localDatasource.getTransaction(id).mapCatching { it.toModel() }
    }

    override suspend fun update(transaction: TransactionParam): Result<Unit> {
        return localDatasource.updateTransaction(transaction.toEntity())
    }
}