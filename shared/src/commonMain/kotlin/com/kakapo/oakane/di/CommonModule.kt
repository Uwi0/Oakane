package com.kakapo.oakane.di

import com.kakapo.data.repository.base.BackupRepository
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.ReportRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.data.repository.impl.BackupRepositoryImpl
import com.kakapo.data.repository.impl.CategoryLimitRepositoryImpl
import com.kakapo.data.repository.impl.CategoryRepositoryImpl
import com.kakapo.data.repository.impl.GoalRepositoryImpl
import com.kakapo.data.repository.impl.MonthlyBudgetRepositoryImpl
import com.kakapo.data.repository.impl.ReportRepositoryImpl
import com.kakapo.data.repository.impl.SystemRepositoryImpl
import com.kakapo.data.repository.impl.TransactionRepositoryImpl
import com.kakapo.data.repository.impl.WalletRepositoryImpl
import com.kakapo.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.database.datasource.base.CategoryLocalDatasource
import com.kakapo.database.datasource.base.GoalLocalDatasource
import com.kakapo.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.database.datasource.base.ReportLocalDatasource
import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.database.datasource.impl.CategoryLimitLocalDatasourceImpl
import com.kakapo.database.datasource.impl.CategoryLocalDatasourceImpl
import com.kakapo.database.datasource.impl.GoalLocalDatasourceImpl
import com.kakapo.database.datasource.impl.MonthlyBudgetLocalDatasourceImpl
import com.kakapo.database.datasource.impl.ReportLocalDatasourceImpl
import com.kakapo.database.datasource.impl.TransactionLocalDatasourceImpl
import com.kakapo.database.datasource.impl.WalletLocalDatasourceImpl
import com.kakapo.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.domain.usecase.base.SaveTransactionUseCase
import com.kakapo.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.domain.usecase.base.ValidateCategoryLimitUseCase
import com.kakapo.domain.usecase.impl.DeleteTransactionUseCaseImpl
import com.kakapo.domain.usecase.impl.GetMonthlyBudgetOverviewUseCaseImpl
import com.kakapo.domain.usecase.impl.SaveTransactionUseCaseImpl
import com.kakapo.domain.usecase.impl.UpdateTransactionUseCaseImpl
import com.kakapo.domain.usecase.impl.ValidateCategoryLimitUseCaseImpl
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalViewModel
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import com.kakapo.oakane.presentation.viewModel.goal.GoalViewModel
import com.kakapo.oakane.presentation.viewModel.goals.GoalsViewModel
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import com.kakapo.oakane.presentation.viewModel.main.MainViewModel
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetViewModel
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingViewModel
import com.kakapo.oakane.presentation.viewModel.reports.ReportsViewModel
import com.kakapo.oakane.presentation.viewModel.settings.SettingsViewModel
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionViewModel
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsViewModel
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.impl.PreferenceDatasourceImpl
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
        factory<ReportLocalDatasource> { ReportLocalDatasourceImpl(get()) }
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
        factory<BackupRepository> { BackupRepositoryImpl(get(), get(), get(), get(), get(), get()) }
        factory<ReportRepository> { ReportRepositoryImpl(get()) }
        factory<SystemRepository> { SystemRepositoryImpl(get()) }
    }

    val domainModule: Module = module {
        factory<ValidateCategoryLimitUseCase> { ValidateCategoryLimitUseCaseImpl(get(), get()) }
        factory<SaveTransactionUseCase> { SaveTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<UpdateTransactionUseCase> { UpdateTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<DeleteTransactionUseCase> { DeleteTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<GetMonthlyBudgetOverviewUseCase> { GetMonthlyBudgetOverviewUseCaseImpl(get(), get()) }
    }

    val viewModel: Module = module {
        viewModel { MainViewModel(get())}
        viewModel { AddTransactionViewModel(get(), get(), get(), get()) }
        viewModel { HomeViewModel(get(), get(), get(), get()) }
        viewModel { TransactionsViewModel(get()) }
        viewModel { TransactionViewModel(get(), get()) }
        viewModel { CategoriesViewModel(get()) }
        viewModel { AddGoalViewModel(get()) }
        viewModel { GoalViewModel(get()) }
        viewModel { GoalsViewModel(get()) }
        viewModel { MonthlyBudgetViewModel(get(), get(), get(), get()) }
        viewModel { WalletsViewModel(get(), get())}
        viewModel { ReportsViewModel(get(), get(), get(), get()) }
        viewModel { SettingsViewModel(get(), get())}
        viewModel { OnBoardingViewModel(get()) }
    }

}

fun initKoin(
    appModule: Module = module {},
    localDatasourceModule: Module = CommonModule.localDatasourceModule,
    preferenceModule: Module = CommonModule.preferenceModule,
    repositoryModule: Module = CommonModule.repositoryModule,
    domainModules: Module = CommonModule.domainModule,
    viewModel: Module = CommonModule.viewModel,
): KoinApplication = startKoin {
    modules(
        appModule,
        localDatasourceModule,
        preferenceModule,
        repositoryModule,
        domainModules,
        viewModel,
        platformModule
    )
}