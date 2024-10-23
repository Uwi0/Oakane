package com.kakapo.oakane.di

import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.datasource.impl.TransactionLocalDatasourceImpl
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.repository.impl.TransactionRepositoryImpl
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionViewModel
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
        viewModel { TransactionsViewModel(get()) }
        viewModel { TransactionViewModel(get()) }
    }
    val coroutineScope = module {
        single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    }
}

fun initKoin(
    appModule: Module = module { },
    localDatasourceModule: Module = CommonModule.localDatasourceModule,
    repositoryModule: Module = CommonModule.repositoryModule,
    viewModel: Module = CommonModule.viewModel,
    coroutineScope: Module = CommonModule.coroutineScope
): KoinApplication = startKoin {
    modules(
        appModule,
        localDatasourceModule,
        repositoryModule,
        viewModel,
        coroutineScope,
        platformModule
    )
}