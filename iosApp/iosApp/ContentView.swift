import SwiftUI
import Shared

struct ContentView: View {
    @State private var isShowingAddTransaction = false
    @State private var isShowingTransactions = false
    var body: some View {
        NavigationStack {
            HomeScreen(isShowingAddTransaction: $isShowingAddTransaction, isShowingTransactions: $isShowingTransactions)
                .navigationDestination(isPresented: $isShowingAddTransaction){
                    AddTransactionScreen()
                }
                .navigationDestination(isPresented: $isShowingTransactions){
                    TransactionsScreen()
                }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
