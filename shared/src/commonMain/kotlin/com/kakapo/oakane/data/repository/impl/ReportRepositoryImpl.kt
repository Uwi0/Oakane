package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.ReportLocalDatasource
import com.kakapo.oakane.data.database.model.ReportEntity
import com.kakapo.oakane.data.model.toReportCsvModel
import com.kakapo.oakane.data.repository.base.ReportRepository
import kotlinx.serialization.json.Json

class ReportRepositoryImpl(
    private val reportLocalDatasource: ReportLocalDatasource
): ReportRepository {

    override suspend fun generateReportAllWallet(): Result<String> {
        return runCatching {
            val reports = reportLocalDatasource.generateReportAllWallet().getOrElse { emptyList() }
            val mappedReport = reports.map(ReportEntity::toReportCsvModel)
            Json.encodeToString(mappedReport)
        }
    }
}