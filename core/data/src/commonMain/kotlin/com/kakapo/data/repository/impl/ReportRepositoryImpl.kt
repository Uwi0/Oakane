package com.kakapo.data.repository.impl

import com.kakapo.data.model.toReportCsvModel
import com.kakapo.data.repository.base.ReportRepository
import com.kakapo.database.datasource.base.ReportLocalDatasource
import com.kakapo.database.model.ReportEntity
import com.kakapo.model.report.ReportCsvModel

class ReportRepositoryImpl(
    private val reportLocalDatasource: ReportLocalDatasource
): ReportRepository {

    override suspend fun generateReports(
        onMonth: String,
        walletId: Long?
    ): Result<List<ReportCsvModel>> {
        return reportLocalDatasource.generateReportAllWallet(onMonth = onMonth, walletId = walletId)
            .mapCatching { it.map(ReportEntity::toReportCsvModel) }
    }
}