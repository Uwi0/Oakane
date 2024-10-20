import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @Environment(\.dismiss) private var dismiss
    @StateObject private var viewModel = TransactionsViewModel()
    
    private var bottomSheetTitlte: String {
        switch(viewModel.bottomSheetContent){
        case .TransactionType:
            "Filter by Type"
        case .DateCreated:
            "Filter by Date"
        case .Categroy:
            "Filter by Category"
        }
    }
    
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                
                TransactionTopAppBarView(
                    query: $viewModel.searchQuery,
                    selectedType: $viewModel.transactionType,
                    selectedDate: $viewModel.dateCreated,
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
            VStack {
                switch viewModel.bottomSheetContent {
                case .TransactionType:
                    FilterTypeView(selectedType: $viewModel.transactionType, onClick: viewModel.hideBottomSheet)
                case .DateCreated:
                    Text("DateCreated")
                case .Categroy:
                    Text("This fearture is not implemented yet")
                }
            }
            .presentationDetents([.height(160), .height(200), .height(240), .medium])
            .presentationDragIndicator(.visible)
        }
        
        
    }
}

#Preview {
    TransactionsScreen()
}
