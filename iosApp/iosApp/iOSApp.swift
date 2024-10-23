import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    @ObservedObject var navigation = AppNavigation()
    
    init () {
        Koin.start()
    }
    
    var body: some Scene {
        WindowGroup {
            NavigationStack(path: $navigation.navPath) {
                HomeScreen()
                    .navigationDestination(for: AppNavigation.Destination.self) { destination in
                        switch destination {
                        case .addTransaction:
                            AddTransactionScreen()
                        case .transactions:
                            TransactionsScreen()
                        case .transaction(let transactionId):
                            TransactionScreen(transactionId: transactionId)
                        }
                    }
                
            }
            .environmentObject(navigation)
        }
    }
}
