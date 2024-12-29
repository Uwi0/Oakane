package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GenerateReportAllWallet
import com.kakapo.oakane.data.database.datasource.base.ReportLocalDatasource
import com.kakapo.oakane.data.database.model.ReportEntity
import com.kakapo.oakane.data.database.model.toReportEntity

class ReportLocalDatasourceImpl(sqlDriver: SqlDriver): ReportLocalDatasource {

    private val reportQuery = Database(sqlDriver).reportQueryQueries

    override suspend fun generateReportAllWallet(
        onMonth: String,
        walletId: Long?
    ): Result<List<ReportEntity>> {
        return runCatching {
            reportQuery.generateReportAllWallet(onMonth, onMonth, walletId)
                .executeAsList()
                .map(GenerateReportAllWallet::toReportEntity)
        }
    }
}