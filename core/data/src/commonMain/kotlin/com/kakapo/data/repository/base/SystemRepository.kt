package com.kakapo.data.repository.base

import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SystemRepository {
    @NativeCoroutines
    suspend fun saveTheme(mode: Theme): Result<Unit>
    @NativeCoroutines
    suspend fun loadSavedTheme(): Result<Theme>
}