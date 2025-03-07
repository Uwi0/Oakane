package com.kakapo.data.repository.base

import com.kakapo.model.report.ReportCsvModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface ReportRepository {
    @NativeCoroutines
    suspend fun generateReports(onMonth: String, walletId: Long?): Result<List<ReportCsvModel>>
}