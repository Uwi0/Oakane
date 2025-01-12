package com.kakapo.data.repository.impl

import com.kakapo.data.model.toReportCsvModel
import com.kakapo.data.repository.base.ReportRepository
import com.kakapo.database.datasource.base.ReportLocalDatasource
import com.kakapo.model.asCurrency
import com.kakapo.model.report.ReportCsvModel
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency

class ReportRepositoryImpl(
    private val reportLocalDatasource: ReportLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource
): ReportRepository {

    override suspend fun generateReports(
        onMonth: String,
        walletId: Long?
    ): Result<List<ReportCsvModel>> {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        return reportLocalDatasource.generateReportAllWallet(onMonth = onMonth, walletId = walletId)
            .mapCatching { reportResult ->
                reportResult.map{ report -> report.toReportCsvModel(currency)}
            }
    }
}