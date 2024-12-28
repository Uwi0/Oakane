package com.kakapo.oakane.data.repository.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface ReportRepository {
    @NativeCoroutines
    suspend fun generateReportAllWallet(): Result<String>
}