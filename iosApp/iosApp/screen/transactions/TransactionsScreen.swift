import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @StateObject private var viewModel = TransactionsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var bottomSheetSize: CGFloat {
        switch(viewModel.bottomSheetContent){
        case .TransactionType:
            160
        case .DateCreated:
            560
        case .Categroy:
            160
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
                    onNavigateBack: { navigation.navigateBack() }
                )
                
                TransactionsView(
                    transactions: viewModel.transactions,
                    deleteTransaction: viewModel.deleteTransacion,
                    onItemClick: { transaction in navigation.navigate(to: .transaction(transactionId: transaction.id)) }
                )
            }
            .onChange(of: viewModel.searchQuery, perform: viewModel.filterBy(query:))
            .onChange(of: viewModel.transactionType, perform: viewModel.filterBy(type:))
            .onChange(of: viewModel.dateCreated, perform: viewModel.filterBy(date:))
        }
        .navigationBarBackButtonHidden(true)
        .sheet(isPresented: $viewModel.showBottomSheet){
            VStack {
                switch viewModel.bottomSheetContent {
                case .TransactionType:
                    FilterByTypeView(
                        selectedType: $viewModel.transactionType,
                        onClick: viewModel.hideBottomSheet
                    )
                case .DateCreated:
                    FilterByDateView(onSelectedDate: viewModel.onSelected(date:))
                case .Categroy:
                    Text("This fearture is not implemented yet")
                }
            }
            .presentationDetents([.height(bottomSheetSize)])
            .presentationDragIndicator(.visible)
        }
        
        
    }
}

#Preview {
    TransactionsScreen()
}
