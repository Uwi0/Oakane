import SwiftUI
import Shared

final class AppNavigation: ObservableObject {
    
    public enum Destination: Codable, Hashable {
        case termAndService
        case onboarding
        case home
        case addTransaction(transactionId: Int64)
        case transactions(showDrawer: Bool = false)
        case transaction(id: Int64)
        case categories(showDrawer: Bool = false)
        case addGoal(goalId: Int64)
        case goal(id: Int64)
        case goals(showDrawer: Bool = false)
        case monthlyBudget
        case wallets(showDrawer: Bool = false)
        case wallet(id: Int64)
        case reports
        case settings
        case reminder
        case createWallet(walletId: Int64 = 0, fromOnBoard: Bool = false)
    }
    
    @Published var navPath = NavigationPath()
    
    func navigate(to destination: Destination) {
        navPath.append(destination)
    }
    
    func navigateBack() {
        if navPath.isEmpty { return }
        navPath.removeLast()
    }
    
    func navigateToRoot() {
        navPath.removeLast(navPath.count)
    }
    
    func navigateFrom(menu: DrawerMenuNavigation){
        switch menu {
        case .dashboard: navigate(to: .home)
        case .transactions: navigate(to: .transactions(showDrawer: true))
        case .categories: navigate(to: .categories(showDrawer: true))
        case .goals: navigate(to: .goals(showDrawer: true))
        case .wallets: navigate(to: .wallets(showDrawer: true))
        case .reports: navigate(to: .reports)
        case .settings: navigate(to: .settings)
        }
    }
    
}
