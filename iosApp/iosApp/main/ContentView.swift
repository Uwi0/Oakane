import SwiftUI

struct ContentView: View {
    
    @State private var openDrawer: Bool = false
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: MainViewModel = MainViewModel()
    @AppStorage(UserDefaultsKeys.isDarkMode) private var isDarkModel: Bool = UserDefaults.standard.bool(forKey: UserDefaultsKeys.isDarkMode)
    
    var body: some View {
        ZStack {
            NavigationStack(path: $navigation.navPath) {
                ScreenContent()
            }
            DrawerMenuView(isShowing: $openDrawer, onMenuClick: navigation.navigateFrom(menu:))
        }
        .preferredColorScheme(isDarkModel ? .dark : .light)
        .task { viewModel.initData() }
    }
    
    @ViewBuilder private func ScreenContent() -> some View {
        SplashScreen()
        .navigationDestination(for: AppNavigation.Destination.self) { destination in
            switch destination {
            case .onboarding: OnBoardingScreen()
            case .home: HomeScreen(openDrawer: $openDrawer)
            case .addTransaction(let transactionId): AddTransactionScreen(transactionId: transactionId)
            case .transactions(let showDrawer): TransactionsScreen(openDrawer: $openDrawer, showDrawer: showDrawer)
            case .transaction(let transactionId): TransactionScreen(transactionId: transactionId)
            case .categories(let showDrawer): CategoriesScreen(openDrawer: $openDrawer, showDrawer: showDrawer)
            case .addGoal(let goalId): AddGoalScreen(goalId: goalId)
            case .goal(let goalId): GoalScreen(goalId: goalId)
            case .goals(let showDrawer): GoalsScreen(openDrawer: $openDrawer, showDrawer: showDrawer)
            case .monthlyBudget: MonthlyBudgetScreen()
            case .wallets(let showDrawer): WalletsScreen(openDrawer: $openDrawer, showDrawer: showDrawer)
            case .reports: ReportsScreen(openDrawer: $openDrawer, showDrawer: true)
            case .settings: SettingsScreen(openDrawer: $openDrawer, showDrawer: true)
            case .wallet(let walletId): WalletScreen(walletId: walletId)
            case .termAndService: TermAndServiceScreen()
            case .reminder: ReminderScreen()
            case .createWallet(let walletId, let fromOnBoard): CreateWalletScreen(walletId: walletId, fromOnBoarding: fromOnBoard)
            }
        }
    }
}

