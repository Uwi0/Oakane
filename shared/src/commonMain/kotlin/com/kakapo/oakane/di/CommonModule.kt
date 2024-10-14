package com.kakapo.oakane.di

import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.datasource.impl.TransactionLocalDatasourceImpl
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.repository.impl.TransactionRepositoryImpl
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import com.kakapo.oakane.presentation.viewModel.transaction.AddTransactionViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {
    val localDatasourceModule: Module = module {
        factory<TransactionLocalDatasource> { TransactionLocalDatasourceImpl(get()) }
    }
    val repositoryModule: Module = module {
        factory<TransactionRepository> { TransactionRepositoryImpl(get()) }
    }
    val viewModel: Module = module {
        viewModel { AddTransactionViewModel(get()) }
        viewModel { HomeViewModel(get()) }
    }
}

fun initKoin(
    appModule: Module = module { },
    localDatasourceModule: Module = CommonModule.localDatasourceModule,
    repositoryModule: Module = CommonModule.repositoryModule,
    viewModel: Module = CommonModule.viewModel
): KoinApplication = startKoin {
    modules(
        appModule,
        localDatasourceModule,
        repositoryModule,
        viewModel,
        platformModule
    )
}