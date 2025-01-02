package com.kakapo.database.datasource.base

import com.kakapo.database.model.ReportEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface ReportLocalDatasource {
    @NativeCoroutines
    suspend fun generateReportAllWallet(onMonth: String, walletId: Long?): Result<List<ReportEntity>>
}