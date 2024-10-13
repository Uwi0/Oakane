package com.kakapo.oakane.di

import com.kakapo.oakane.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.database.datasource.impl.TransactionLocalDatasourceImpl
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {
    val localDatasourceModule: Module = module {
        factory<TransactionLocalDatasource> { TransactionLocalDatasourceImpl(get()) }
    }
}

fun initKoin(
    appModule: Module = module {  },
    localDatasourceModule: Module = CommonModule.localDatasourceModule
): KoinApplication = startKoin {
    modules(
        appModule,
        localDatasourceModule,
        platformModule
    )
}