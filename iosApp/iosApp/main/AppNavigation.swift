import SwiftUI
import Shared

final class AppNavigation: ObservableObject {
    
    public enum Destination: Codable, Hashable {
        case addTransaction(transactionId: Int64)
        case transactions
        case transaction(transactionId: Int64)
        case categories
        case addGoal
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
        }
    }
    
}
