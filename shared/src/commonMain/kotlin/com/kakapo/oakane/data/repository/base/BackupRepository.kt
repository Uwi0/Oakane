package com.kakapo.oakane.data.repository.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface BackupRepository {
    @NativeCoroutines
    suspend fun createBackup(): Result<String>

}