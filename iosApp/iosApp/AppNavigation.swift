import SwiftUI


final class AppNavigation: ObservableObject {
    
    public enum Destination: Codable, Hashable {
        case addTransaction
        case transactions
        case transaction(transactionId: Int64)
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
    
}
