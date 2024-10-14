package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.common.proceed
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource

class TransactionRepositoryImpl(
    private val localDatasource: TransactionLocalDatasource
) : TransactionRepository{

    override suspend fun save(transaction: TransactionParam): Result<Unit> {
        return proceed { localDatasource.insertTransaction(transaction.toEntity()) }
    }
}