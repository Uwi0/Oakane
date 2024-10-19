import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @Environment(\.dismiss) private var dismiss
    @StateObject private var viewModel = TransactionsViewModel()
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                TransactionTopAppBarView(query: $viewModel.searchQuery, onNavigateBack: { dismiss() })
                TransactionsView(transactions: viewModel.transactions)
            }
            .onChange(of: viewModel.searchQuery, perform: viewModel.filterBy(query:))
        }
        .navigationBarBackButtonHidden(true)
        
        
    }
}

#Preview {
    TransactionsScreen()
}
