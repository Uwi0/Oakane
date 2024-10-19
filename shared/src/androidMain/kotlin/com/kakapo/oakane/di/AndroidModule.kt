package com.kakapo.oakane.di

import com.kakapo.oakane.data.database.MySqlDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        MySqlDriverFactory(get()).createDriver()
    }
}
