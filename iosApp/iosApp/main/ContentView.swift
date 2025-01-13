import SwiftUI

struct ContentView: View {
    
    @State private var showDrawer: Bool = false
    @EnvironmentObject private var navigation: AppNavigation
    @AppStorage(UserDefaultsKeys.isDarkMode) private var isDarkModel: Bool = UserDefaults.standard.bool(forKey: UserDefaultsKeys.isDarkMode)
    @AppStorage(UserDefaultsKeys.onBoardingAlreadyRead) private var onBoardingAlreadyRead: Bool = UserDefaults.standard.bool(forKey: UserDefaultsKeys.onBoardingAlreadyRead)
    
    var body: some View {
        NavigationStack(path: $navigation.navPath) {
            ZStack {
                ScreenContent()
                DrawerMenuView(isShowing: $showDrawer, onMenuClick: navigation.navigateFrom(menu:))
            }
        }
        .preferredColorScheme(isDarkModel ? .dark : .light)
    }
    
    @ViewBuilder private func ScreenContent() -> some View {
        VStack {
            if onBoardingAlreadyRead {
                HomeScreen(showDrawer: $showDrawer)
            }else {
                OnBoardingScreen()
            }
        }
        .navigationDestination(for: AppNavigation.Destination.self) { destination in
            switch destination {
            case .addTransaction(let transactionId): AddTransactionScreen(transactionId: transactionId)
            case .transactions: TransactionsScreen()
            case .transaction(let transactionId): TransactionScreen(transactionId: transactionId)
            case .categories: CategoriesScreen()
            case .addGoal(let goalId): AddGoalScreen(goalId: goalId)
            case .goal(let goalId): GoalScreen(goalId: goalId)
            case .goals: GoalsScreen()
            case .monthlyBudget: MonthlyBudgetScreen()
            case .wallets: WalletsScreen()
            case .reports: ReportsScreen()
            case .settings: SettingsScreen()
            case .home: HomeScreen(showDrawer: $showDrawer)
            }
        }
    }
}

