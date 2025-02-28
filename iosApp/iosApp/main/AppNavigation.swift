import SwiftUI
import Shared

final class AppNavigation: ObservableObject {
    
    public enum Destination: Codable, Hashable {
        case onboarding
        case home
        case addTransaction(transactionId: Int64)
        case transactions
        case transaction(transactionId: Int64)
        case categories
        case addGoal(goalId: Int64)
        case goal(goalId: Int64)
        case goals
        case monthlyBudget
        case wallets
        case wallet(walletId: Int64)
        case reports
        case settings
    }
    
    @Published var navPath = NavigationPath()
    
    func navigate(to destination: Destination) {
        navPath.append(destination)
    }
    
    func navigateBack() {
        navPath.removeLast()
    }
    
    func navigateToRoot() {
        navPath.removeLast(navPath.count)
    }
    
    func navigateFrom(menu: DrawerMenuNavigation){
        switch menu {
        case .dashboard:
            print("Dashboard")
        case .transactions:
            navigate(to: .transactions)
        case .categories:
            navigate(to: .categories)
        case .goals:
            navigate(to: .goals)
        case .wallets:
            navigate(to: .wallets)
        case .reports:
            navigate(to: .reports)
        case .settings:
            navigate(to: .settings)
        }
    }
    
}
