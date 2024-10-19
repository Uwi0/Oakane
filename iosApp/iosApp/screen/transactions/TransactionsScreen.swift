import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @Environment(\.dismiss) private var dismiss
    @StateObject private var viewModel = TransactionsViewModel()
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                TransactionTopAppBarView(
                    query: $viewModel.searchQuery,
                    selectedType: viewModel.transactionType,
                    selectedDate: viewModel.dateCreated,
                    selectedCategory: "",
                    onClick: viewModel.onShowBottomSheet,
                    onNavigateBack: { dismiss() }
                )
                TransactionsView(transactions: viewModel.transactions)
            }
            .onChange(of: viewModel.searchQuery, perform: viewModel.filterBy(query:))
            .onChange(of: viewModel.transactionType, perform: viewModel.filterBy(type:))
        }
        .navigationBarBackButtonHidden(true)
        .sheet(isPresented: $viewModel.showBottomSheet){
            switch viewModel.bottomSheetContent {
            case .TransactionType:
                Text("TransactionType")
            case .DateCreated:
                Text("DateCreated")
            case .Categroy:
                Text("Categroy")
            }
        }
        
        
    }
}

#Preview {
    TransactionsScreen()
}
