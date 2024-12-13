package com.kakapo.oakane.di

import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.WalletLocalDatasource
import com.kakapo.oakane.data.database.datasource.impl.CategoryLimitLocalDatasourceImpl
import com.kakapo.oakane.data.database.datasource.impl.CategoryLocalDatasourceImpl
import com.kakapo.oakane.data.database.datasource.impl.GoalLocalDatasourceImpl
import com.kakapo.oakane.data.database.datasource.impl.MonthlyBudgetLocalDatasourceImpl
import com.kakapo.oakane.data.database.datasource.impl.TransactionLocalDatasourceImpl
import com.kakapo.oakane.data.database.datasource.impl.WalletLocalDatasourceImpl
import com.kakapo.oakane.data.preference.datasource.base.PreferenceDatasource
import com.kakapo.oakane.data.preference.datasource.impl.PreferenceDatasourceImpl
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.data.repository.base.GoalRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.repository.base.WalletRepository
import com.kakapo.oakane.data.repository.impl.CategoryLimitRepositoryImpl
import com.kakapo.oakane.data.repository.impl.CategoryRepositoryImpl
import com.kakapo.oakane.data.repository.impl.GoalRepositoryImpl
import com.kakapo.oakane.data.repository.impl.MonthlyBudgetRepositoryImpl
import com.kakapo.oakane.data.repository.impl.TransactionRepositoryImpl
import com.kakapo.oakane.data.repository.impl.WalletRepositoryImpl
import com.kakapo.oakane.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.oakane.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.oakane.domain.usecase.base.SaveTransactionUseCase
import com.kakapo.oakane.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.oakane.domain.usecase.base.ValidateCategoryLimitUseCase
import com.kakapo.oakane.domain.usecase.impl.DeleteTransactionUseCaseImpl
import com.kakapo.oakane.domain.usecase.impl.GetMonthlyBudgetOverviewUseCaseImpl
import com.kakapo.oakane.domain.usecase.impl.SaveTransactionUseCaseImpl
import com.kakapo.oakane.domain.usecase.impl.UpdateTransactionUseCaseImpl
import com.kakapo.oakane.domain.usecase.impl.ValidateCategoryLimitUseCaseImpl
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalViewModel
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import com.kakapo.oakane.presentation.viewModel.goal.GoalViewModel
import com.kakapo.oakane.presentation.viewModel.goals.GoalsViewModel
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetViewModel
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionViewModel
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsViewModel
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
        factory<CategoryLocalDatasource> { CategoryLocalDatasourceImpl(get()) }
        factory<GoalLocalDatasource> { GoalLocalDatasourceImpl(get()) }
        factory<MonthlyBudgetLocalDatasource> { MonthlyBudgetLocalDatasourceImpl(get()) }
        factory<CategoryLimitLocalDatasource> { CategoryLimitLocalDatasourceImpl(get()) }
        factory<WalletLocalDatasource> { WalletLocalDatasourceImpl(get()) }
    }

    val preferenceModule: Module = module {
        factory<PreferenceDatasource> { PreferenceDatasourceImpl(get()) }
    }

    val repositoryModule: Module = module {
        factory<TransactionRepository> { TransactionRepositoryImpl(get()) }
        factory<CategoryRepository> { CategoryRepositoryImpl(get()) }
        factory<GoalRepository> { GoalRepositoryImpl(get()) }
        factory<MonthlyBudgetRepository> { MonthlyBudgetRepositoryImpl(get()) }
        factory<CategoryLimitRepository> { CategoryLimitRepositoryImpl(get()) }
        factory<WalletRepository> { WalletRepositoryImpl(get(), get()) }
    }

    val domainModule: Module = module {
        factory<ValidateCategoryLimitUseCase> { ValidateCategoryLimitUseCaseImpl(get(), get()) }
        factory<SaveTransactionUseCase> { SaveTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<UpdateTransactionUseCase> { UpdateTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<DeleteTransactionUseCase> { DeleteTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<GetMonthlyBudgetOverviewUseCase> { GetMonthlyBudgetOverviewUseCaseImpl(get(), get()) }
    }

    val viewModel: Module = module {
        viewModel { AddTransactionViewModel(get(), get(), get(), get()) }
        viewModel { HomeViewModel(get(), get(), get(), get()) }
        viewModel { TransactionsViewModel(get()) }
        viewModel { TransactionViewModel(get(), get()) }
        viewModel { CategoriesViewModel(get()) }
        viewModel { AddGoalViewModel(get()) }
        viewModel { GoalViewModel(get()) }
        viewModel { GoalsViewModel(get()) }
        viewModel { MonthlyBudgetViewModel(get(), get(), get(), get()) }
        viewModel { WalletsViewModel()}
    }

    val coroutineScope = module {
        single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    }
}

fun initKoin(
    appModule: Module = module {},
    localDatasourceModule: Module = CommonModule.localDatasourceModule,
    preferenceModule: Module = CommonModule.preferenceModule,
    repositoryModule: Module = CommonModule.repositoryModule,
    domainModules: Module = CommonModule.domainModule,
    viewModel: Module = CommonModule.viewModel,
    coroutineScope: Module = CommonModule.coroutineScope
): KoinApplication = startKoin {
    modules(
        appModule,
        localDatasourceModule,
        preferenceModule,
        repositoryModule,
        domainModules,
        viewModel,
        coroutineScope,
        platformModule
    )
}