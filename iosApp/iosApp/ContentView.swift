import SwiftUI
import Shared

struct ContentView: View {
    @State private var isShowingAddTransaction = false
    var body: some View {
        NavigationStack {
            HomeScreen(isShowingAddTransaction: $isShowingAddTransaction)
                .navigationDestination(isPresented: $isShowingAddTransaction){
                    AddTransactionScreen()
                }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
