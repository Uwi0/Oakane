package com.kakapo.oakane.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.feature.OakaneAppState
import com.kakapo.oakane.presentation.feature.addGoal.navigation.addGoalScreen
import com.kakapo.oakane.presentation.feature.addGoal.navigation.navigateToAddGoal
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.addTransactionScreen
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.navigateToAddTransaction
import com.kakapo.oakane.presentation.feature.categories.navigation.categoriesScreen
import com.kakapo.oakane.presentation.feature.createwallet.navigation.createWalletScreen
import com.kakapo.oakane.presentation.feature.createwallet.navigation.navigateToCreateWallet
import com.kakapo.oakane.presentation.feature.goal.navigation.goalScreen
import com.kakapo.oakane.presentation.feature.goal.navigation.navigateToGoal
import com.kakapo.oakane.presentation.feature.goals.navigation.goalsScreen
import com.kakapo.oakane.presentation.feature.goals.navigation.navigateToGoals
import com.kakapo.oakane.presentation.feature.home.navigation.homeScreen
import com.kakapo.oakane.presentation.feature.home.navigation.navigateToHome
import com.kakapo.oakane.presentation.feature.monthlyBudget.navigation.monthlyBudgetScreen
import com.kakapo.oakane.presentation.feature.monthlyBudget.navigation.navigateToMonthlyBudget
import com.kakapo.oakane.presentation.feature.onboarding.navigation.navigateToOnBoarding
import com.kakapo.oakane.presentation.feature.onboarding.navigation.onBoardingScreen
import com.kakapo.oakane.presentation.feature.reminder.navigation.navigateToReminder
import com.kakapo.oakane.presentation.feature.reminder.navigation.reminderScreen
import com.kakapo.oakane.presentation.feature.reports.navigation.reportsScreen
import com.kakapo.oakane.presentation.feature.settings.navigation.settingsScreen
import com.kakapo.oakane.presentation.feature.splash.navigation.SPLASH_ROUTE
import com.kakapo.oakane.presentation.feature.splash.navigation.splashScreen
import com.kakapo.oakane.presentation.feature.termAndService.navigation.navigateToTermAndService
import com.kakapo.oakane.presentation.feature.termAndService.navigation.termAndServiceScreen
import com.kakapo.oakane.presentation.feature.transaction.navigation.navigateToTransaction
import com.kakapo.oakane.presentation.feature.transaction.navigation.transactionScreen
import com.kakapo.oakane.presentation.feature.transactions.navigation.navigateToTransactions
import com.kakapo.oakane.presentation.feature.transactions.navigation.transactionsScreen
import com.kakapo.oakane.presentation.feature.wallet.navigation.navigateToWallet
import com.kakapo.oakane.presentation.feature.wallet.navigation.walletScreen
import com.kakapo.oakane.presentation.feature.wallets.navigation.navigateToWallets
import com.kakapo.oakane.presentation.feature.wallets.navigation.walletsScreen

@Composable
internal fun OakaneNavHost(
    appState: OakaneAppState,
    startDestination: String = SPLASH_ROUTE,
    openDrawer: () -> Unit,
    onSelectedTheme: (Theme) -> Unit
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        splashScreen(
            navigateToTermAndService = { navController.navigateToTermAndService(appState.navOptionsPopBackStack()) },
            navigateToHome = { navController.navigateToHome(appState.navOptionsPopBackStack()) },
            navigateToOnBoarding = { navController.navigateToOnBoarding(appState.navOptionsPopBackStack()) }
        )
        termAndServiceScreen(
            navigateToOnBoarding = { navController.navigateToOnBoarding(appState.navOptionsPopBackStack()) }
        )
        onBoardingScreen(
            navigateToHome = { navController.navigateToHome(appState.navOptionsPopBackStack()) },
            navigateToCreateWallet = navController::navigateToCreateWallet
        )
        homeScreen(
            openDrawer = openDrawer,
            navigateToAddTransaction = navController::navigateToAddTransaction,
            navigateToTransactions = navController::navigateToTransactions,
            navigateToTransaction = navController::navigateToTransaction,
            navigateToAddGoal = navController::navigateToAddGoal,
            navigateToGoals = navController::navigateToGoals,
            navigateToGoal = navController::navigateToGoal,
            navigateToMonthlyBudget = navController::navigateToMonthlyBudget,
            navigateToWallets = navController::navigateToWallets
        )
        addTransactionScreen(navigateBack = appState::safeNavigateUp)
        transactionsScreen(
            openDrawer = openDrawer,
            navigateBack = appState::safeNavigateUp,
            navigateToTransaction = navController::navigateToTransaction
        )
        transactionScreen(
            navigateToEdit = navController::navigateToAddTransaction,
            navigateBack = navController::navigateUp
        )
        categoriesScreen(
            openDrawer = openDrawer,
            navigateBack = appState::safeNavigateUp
        )
        addGoalScreen(
            navigateBack = appState::safeNavigateUp
        )
        goalsScreen(
            openDrawer = openDrawer,
            navigateUp = appState::safeNavigateUp,
            navigateToAddGoal = navController::navigateToAddGoal,
            navigateToGoal = navController::navigateToGoal
        )
        goalScreen(
            navigateUp = appState::safeNavigateUp,
            updateGoal = navController::navigateToAddGoal
        )
        monthlyBudgetScreen(
            navigateBack = appState::safeNavigateUp
        )
        walletsScreen(
            openDrawer = openDrawer,
            navigateBack = appState::safeNavigateUp,
            navigateToWallet = navController::navigateToWallet,
            navigateToCreateWallet = navController::navigateToCreateWallet
        )
        walletScreen(
            navigateBack = navController::navigateUp,
            navigateToCreateWallet = navController::navigateToCreateWallet
        )
        reportsScreen(
            openDrawer = openDrawer,
            navigateBack = appState::safeNavigateUp
        )
        settingsScreen(
            openDrawer = openDrawer,
            navigateBack = appState::safeNavigateUp,
            onSelectedTheme = onSelectedTheme,
            navigateToReminder = navController::navigateToReminder
        )
        reminderScreen(navigateBack = navController::navigateUp)
        createWalletScreen(
            navigateBack = navController::navigateUp,
            navigateToHome = { navController.navigateToHome(appState.navOptionsPopBackStack()) }
        )
    }
}