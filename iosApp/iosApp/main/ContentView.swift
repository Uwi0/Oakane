import SwiftUI

struct ContentView: View {
    
    @State private var showDrawer: Bool = false
    @EnvironmentObject private var navigation: AppNavigation
    
    
    var body: some View {
        NavigationStack(path: $navigation.navPath) {
            ZStack {
                HomeScreen(showDrawer: $showDrawer)
                    .navigationDestination(for: AppNavigation.Destination.self) { destination in
                        switch destination {
                        case .addTransaction(let transactionId):
                            AddTransactionScreen(transactionId: transactionId)
                        case .transactions:
                            TransactionsScreen()
                        case .transaction(let transactionId):
                            TransactionScreen(transactionId: transactionId)
                        case .categories:
                            CategoriesScreen()
                        case .addGoal(let goalId):
                            AddGoalScreen(goalId: goalId)
                        case .goal(let goalId):
                            GoalScreen(goalId: goalId)
                        case .goals:
                            GoalsScreen()
                        case .monthlyBudget:
                            MonthlyBudgetScreen()
                        case .wallets:
                            WalletsScreen()
                        }
                    }
                
                DrawerMenuView(isShowing: $showDrawer, onMenuClick: navigation.navigateFrom(menu:))
            }
        }
    }
}

