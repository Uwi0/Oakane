package com.kakapo.data.repository.base

interface BackupRepository {
    suspend fun createBackup(): Result<String>
    suspend fun restoreBackup(backup: String): Result<Unit>
}