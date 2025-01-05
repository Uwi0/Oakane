package com.kakapo.oakane.di

import com.kakapo.database.MySqlDriverFactory
import com.kakapo.preference.OakanePreferenceDataStoreFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { MySqlDriverFactory(get()).createDriver() }
    single { OakanePreferenceDataStoreFactory(get()).dataStore() }
}
