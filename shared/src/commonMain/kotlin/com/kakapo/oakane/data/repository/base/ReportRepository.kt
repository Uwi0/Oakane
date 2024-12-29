package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.report.ReportCsvModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface ReportRepository {
    @NativeCoroutines
    suspend fun generateReportAllWallet(onMonth: String, walletId: Long?): Result<List<ReportCsvModel>>
}