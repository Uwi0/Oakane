package com.kakapo.oakane.data.repository.impl

import com.kakapo.model.system.Theme
import com.kakapo.oakane.data.preference.constant.IntKey
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource
import com.kakapo.oakane.data.preference.datasource.utils.getThemeMode
import com.kakapo.oakane.data.repository.base.SystemRepository

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
            preferenceDatasource.getThemeMode()
        }
    }
}
