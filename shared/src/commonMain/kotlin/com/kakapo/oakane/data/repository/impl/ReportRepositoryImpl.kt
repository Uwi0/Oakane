package com.kakapo.oakane.data.repository.impl

import co.touchlab.kermit.Logger
import com.kakapo.database.datasource.base.ReportLocalDatasource
import com.kakapo.database.model.ReportEntity
import com.kakapo.oakane.data.model.toReportCsvModel
import com.kakapo.oakane.data.repository.base.ReportRepository
import com.kakapo.oakane.model.report.ReportCsvModel

class ReportRepositoryImpl(
    private val reportLocalDatasource: ReportLocalDatasource
): ReportRepository {

    override suspend fun generateReports(
        onMonth: String,
        walletId: Long?
    ): Result<List<ReportCsvModel>> {
        Logger.d("generateReports $onMonth, $walletId")
        return reportLocalDatasource.generateReportAllWallet(onMonth = onMonth, walletId = walletId)
            .mapCatching { it.map(ReportEntity::toReportCsvModel) }
    }
}