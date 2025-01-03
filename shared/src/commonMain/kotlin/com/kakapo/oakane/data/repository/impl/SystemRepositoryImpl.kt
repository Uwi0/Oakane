package com.kakapo.oakane.data.repository.impl

import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import com.kakapo.oakane.data.repository.base.SystemRepository
import com.kakapo.preference.constant.IntKey
import com.kakapo.preference.datasource.base.PreferenceDatasource

class SystemRepositoryImpl(
    private val preferenceDatasource: PreferenceDatasource
) : SystemRepository {

    override suspend fun saveTheme(mode: Theme): Result<Unit> {
        return runCatching {
            preferenceDatasource.saveIntValue(IntKey.THEME_MODE, mode.ordinal)
        }
    }

    override suspend fun loadSavedTheme(): Result<Theme> {
        return runCatching {
            val savedTheme = preferenceDatasource.getIntValue(IntKey.THEME_MODE)
            savedTheme.asTheme()
        }
    }
}
