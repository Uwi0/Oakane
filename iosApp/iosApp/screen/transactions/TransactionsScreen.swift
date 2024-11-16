import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @StateObject private var viewModel = TransactionsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var bottomSheetSize: CGFloat {
        switch (viewModel.uiState.sheetContent) {
        case .type: 160
        case .date: 560
        case .category: 640
        }
    }
    
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                
                TransactionTopAppBarView(
                    uiState: viewModel.uiState,
                    onEvent: viewModel.handle(event:)
                )
                
                TransactionsView(
                    transactions: viewModel.uiState.transactions,
                    onEvent: viewModel.handle(event:)
                )
            }

        }
        .navigationBarBackButtonHidden(true)
        .sheet(isPresented: $viewModel.uiState.sheetSown){
            VStack {
                switch viewModel.uiState.sheetContent {
                case .type:
                    FilterByTypeView(
                        uiState: viewModel.uiState,
                        onEvent: viewModel.handle(event:)
                    )
                case .date:
                    FilterByDateView(onEvent: viewModel.handle(event:))
                case .category:
                    Text("This fearture is not implemented yet")
                }
            }
            .presentationDetents([.height(bottomSheetSize)])
            .presentationDragIndicator(.visible)
        }
        .onChange(of: viewModel.uiEffect, perform: observe(effect:))
    }
    
    private func observe(effect: TransactionsEffect?) {
        if let sideEffect = effect {
            switch onEnum(of: sideEffect) {
            case .hideSheet: print("Hide sheet")
            case .navigateBack: navigation.navigateBack()
            case .toDetail(let effect):
                navigation.navigate(to: .transaction(transactionId: effect.id))
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    TransactionsScreen()
}
