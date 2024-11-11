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
            ContentView()
            .environmentObject(navigation)
        }
    }
}
