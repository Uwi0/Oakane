import SwiftUI

struct TransactionsScreen: View {
    @Environment(\.dismiss) private var dismiss
    @State var query: String = ""
    @StateObject private var viewModel = TransactionsViewModel()
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                TransactionTopAppBarView(query: $query, onNavigateBack: { dismiss() })
                TransactionsView(transactions: viewModel.transactions)
            }
        }
        .navigationBarBackButtonHidden(true)
        
        
    }
}

#Preview {
    TransactionsScreen()
}
