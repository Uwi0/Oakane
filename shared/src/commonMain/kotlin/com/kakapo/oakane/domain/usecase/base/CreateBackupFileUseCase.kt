package com.kakapo.oakane.domain.usecase.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CreateBackupFileUseCase {
    @NativeCoroutines
    suspend fun createBackupFile(): Result<String>
}