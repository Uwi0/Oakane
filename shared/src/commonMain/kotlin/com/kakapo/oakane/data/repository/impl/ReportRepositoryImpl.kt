package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.ReportLocalDatasource
import com.kakapo.oakane.data.database.model.ReportEntity
import com.kakapo.oakane.data.model.toReportCsvModel
import com.kakapo.oakane.data.repository.base.ReportRepository
import com.kakapo.oakane.model.report.ReportCsvModel

class ReportRepositoryImpl(
    private val reportLocalDatasource: ReportLocalDatasource
): ReportRepository {

    override suspend fun generateReportAllWallet(): Result<List<ReportCsvModel>> {
        return reportLocalDatasource.generateReportAllWallet()
            .mapCatching { it.map(ReportEntity::toReportCsvModel) }
    }
}