package com.kakapo.oakane.di

import com.kakapo.data.repository.base.BackupRepository
import com.kakapo.data.repository.base.BudgetRepository
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.GoalSavingsRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.ReportRepository
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.data.repository.base.WalletTransferRepository
import com.kakapo.data.repository.impl.BackupRepositoryImpl
import com.kakapo.data.repository.impl.BudgetRepositoryImpl
import com.kakapo.data.repository.impl.CategoryLimitRepositoryImpl
import com.kakapo.data.repository.impl.CategoryRepositoryImpl
import com.kakapo.data.repository.impl.GoalRepositoryImpl
import com.kakapo.data.repository.impl.GoalSavingsRepositoryImpl
import com.kakapo.data.repository.impl.MonthlyBudgetRepositoryImpl
import com.kakapo.data.repository.impl.ReportRepositoryImpl
import com.kakapo.data.repository.impl.SettingsRepositoryImpl
import com.kakapo.data.repository.impl.SystemRepositoryImpl
import com.kakapo.data.repository.impl.TransactionRepositoryImpl
import com.kakapo.data.repository.impl.WalletRepositoryImpl
import com.kakapo.data.repository.impl.WalletTransferRepositoryImpl
import com.kakapo.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.database.datasource.base.CategoryLocalDatasource
import com.kakapo.database.datasource.base.GoalLocalDatasource
import com.kakapo.database.datasource.base.GoalSavingsLocalDatasource
import com.kakapo.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.database.datasource.base.ReportLocalDatasource
import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.database.datasource.base.WalletTransferLocalDatasource
import com.kakapo.database.datasource.impl.CategoryLimitLocalDatasourceImpl
import com.kakapo.database.datasource.impl.CategoryLocalDatasourceImpl
import com.kakapo.database.datasource.impl.GoalLocalDatasourceImpl
import com.kakapo.database.datasource.impl.GoalSavingsLocalDatasourceImpl
import com.kakapo.database.datasource.impl.MonthlyBudgetLocalDatasourceImpl
import com.kakapo.database.datasource.impl.ReportLocalDatasourceImpl
import com.kakapo.database.datasource.impl.TransactionLocalDatasourceImpl
import com.kakapo.database.datasource.impl.WalletLocalDatasourceImpl
import com.kakapo.database.datasource.impl.WalletTransferLocalDatasourceImpl
import com.kakapo.domain.usecase.base.AddGoalSavingUseCase
import com.kakapo.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.domain.usecase.base.ImportRecurringBudgetUseCase
import com.kakapo.domain.usecase.base.MoveWalletBalanceUseCase
import com.kakapo.domain.usecase.base.SaveGoalUseCase
import com.kakapo.domain.usecase.base.SaveTransactionUseCase
import com.kakapo.domain.usecase.base.SetRecurringBudgetUseCase
import com.kakapo.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.domain.usecase.base.ValidateCategoryLimitUseCase
import com.kakapo.domain.usecase.base.WalletLogItemsUseCase
import com.kakapo.domain.usecase.impl.AddGoalSavingUseCaseImpl
import com.kakapo.domain.usecase.impl.DeleteTransactionUseCaseImpl
import com.kakapo.domain.usecase.impl.GetMonthlyBudgetOverviewUseCaseImpl
import com.kakapo.domain.usecase.impl.ImportRecurringBudgetUseCaseImpl
import com.kakapo.domain.usecase.impl.MoveWalletBalanceUseCaseImpl
import com.kakapo.domain.usecase.impl.SaveGoalUseCaseImpl
import com.kakapo.domain.usecase.impl.SaveTransactionUseCaseImpl
import com.kakapo.domain.usecase.impl.SetRecurringBudgetUseCaseImpl
import com.kakapo.domain.usecase.impl.UpdateTransactionUseCaseImpl
import com.kakapo.domain.usecase.impl.ValidateCategoryLimitUseCaseImpl
import com.kakapo.domain.usecase.impl.WalletLogItemsUseCaseImpl
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
import com.kakapo.oakane.presentation.viewModel.splash.SplashViewModel
import com.kakapo.oakane.presentation.viewModel.termAndService.TermAndServiceViewModel
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionViewModel
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import com.kakapo.oakane.presentation.viewModel.wallet.WalletViewModel
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsViewModel
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.impl.PreferenceDatasourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {

    private const val IO = "IO"

    val localDatasourceModule: Module = module {
        factory<TransactionLocalDatasource> { TransactionLocalDatasourceImpl(get(), get(named(IO))) }
        factory<CategoryLocalDatasource> { CategoryLocalDatasourceImpl(get()) }
        factory<GoalLocalDatasource> { GoalLocalDatasourceImpl(get()) }
        factory<MonthlyBudgetLocalDatasource> { MonthlyBudgetLocalDatasourceImpl(get()) }
        factory<CategoryLimitLocalDatasource> { CategoryLimitLocalDatasourceImpl(get()) }
        factory<WalletLocalDatasource> { WalletLocalDatasourceImpl(get(), get(named(IO))) }
        factory<ReportLocalDatasource> { ReportLocalDatasourceImpl(get()) }
        factory<GoalSavingsLocalDatasource> { GoalSavingsLocalDatasourceImpl(get(), get(named(IO))) }
        factory<WalletTransferLocalDatasource> { WalletTransferLocalDatasourceImpl(get(), get(named(IO))) }
    }

    val preferenceModule: Module = module {
        factory<PreferenceDatasource> { PreferenceDatasourceImpl(get()) }
    }

    val repositoryModule: Module = module {
        factory<TransactionRepository> { TransactionRepositoryImpl(get(), get(), get(named(IO))) }
        factory<CategoryRepository> { CategoryRepositoryImpl(get()) }
        factory<GoalRepository> { GoalRepositoryImpl(get(), get()) }
        factory<MonthlyBudgetRepository> { MonthlyBudgetRepositoryImpl(get(), get()) }
        factory<CategoryLimitRepository> { CategoryLimitRepositoryImpl(get(), get()) }
        factory<WalletRepository> { WalletRepositoryImpl(get(), get(), get(named(IO))) }
        factory<BackupRepository> { BackupRepositoryImpl(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(named(IO))) }
        factory<ReportRepository> { ReportRepositoryImpl(get(), get()) }
        factory<SystemRepository> { SystemRepositoryImpl(get()) }
        factory<GoalSavingsRepository> { GoalSavingsRepositoryImpl(get(), get(named(IO))) }
        factory<WalletTransferRepository> { WalletTransferRepositoryImpl(get(), get(), get(named(IO))) }
        factory<BudgetRepository> { BudgetRepositoryImpl(get()) }
        factory<SettingsRepository> { SettingsRepositoryImpl(get()) }
    }

    val domainModule: Module = module {
        factory<ValidateCategoryLimitUseCase> { ValidateCategoryLimitUseCaseImpl(get(), get()) }
        factory<SaveTransactionUseCase> { SaveTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<UpdateTransactionUseCase> { UpdateTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<DeleteTransactionUseCase> { DeleteTransactionUseCaseImpl(get(), get(), get(), get()) }
        factory<GetMonthlyBudgetOverviewUseCase> { GetMonthlyBudgetOverviewUseCaseImpl(get(), get(), get()) }
        factory<AddGoalSavingUseCase> { AddGoalSavingUseCaseImpl(get(), get(), get(), get(named(IO))) }
        factory<MoveWalletBalanceUseCase> { MoveWalletBalanceUseCaseImpl(get(), get(), get(named(IO))) }
        factory<WalletLogItemsUseCase> { WalletLogItemsUseCaseImpl(get(), get(), get(), get(named(IO))) }
        factory<SetRecurringBudgetUseCase> { SetRecurringBudgetUseCaseImpl(get(), get(named(IO))) }
        factory<ImportRecurringBudgetUseCase> { ImportRecurringBudgetUseCaseImpl(get(), get(), get(), get(named(IO))) }
        factory<SaveGoalUseCase> { SaveGoalUseCaseImpl(get()) }
    }

    val viewModel: Module = module {
        viewModel { SplashViewModel(get()) }
        viewModel { MainViewModel(get(), get()) }
        viewModel { AddTransactionViewModel(get(), get(), get(), get(), get(), get()) }
        viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
        viewModel { TransactionsViewModel(get(), get()) }
        viewModel { TransactionViewModel(get(), get(), get(), get()) }
        viewModel { CategoriesViewModel(get(), get()) }
        viewModel { AddGoalViewModel(get(), get(), get()) }
        viewModel { GoalViewModel(get(), get(), get(), get(), get()) }
        viewModel { GoalsViewModel(get(), get()) }
        viewModel { MonthlyBudgetViewModel(get(), get(), get(), get(), get(), get()) }
        viewModel { WalletsViewModel(get(), get())}
        viewModel { ReportsViewModel(get(), get(), get(), get(), get()) }
        viewModel { SettingsViewModel(get(), get(), get(), get())}
        viewModel { OnBoardingViewModel(get(), get(), get(), get()) }
        viewModel { WalletViewModel(get(), get(), get(), get()) }
        viewModel { TermAndServiceViewModel(get()) }
    }

    val coroutineModule: Module = module {
        single(qualifier = named(IO)) { Dispatchers.IO }
    }
}

fun initKoin(
    appModule: Module = module {},
    localDatasourceModule: Module = CommonModule.localDatasourceModule,
    preferenceModule: Module = CommonModule.preferenceModule,
    repositoryModule: Module = CommonModule.repositoryModule,
    domainModules: Module = CommonModule.domainModule,
    viewModel: Module = CommonModule.viewModel,
    coroutineModule: Module = CommonModule.coroutineModule
): KoinApplication = startKoin {
    modules(
        appModule,
        localDatasourceModule,
        preferenceModule,
        repositoryModule,
        domainModules,
        viewModel,
        coroutineModule,
        platformModule
    )
}