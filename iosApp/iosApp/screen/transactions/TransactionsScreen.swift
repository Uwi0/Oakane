import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @StateObject private var viewModel = TransactionsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var bottomSheetSize: CGFloat {
        switch (viewModel.uiState.sheetContent) {
        case .type: 160
        case .date: 560
        case .category: 480
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
        .sheet(isPresented: $viewModel.uiState.sheetShown){
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
                    FilterByCategoryView(
                        uiState: viewModel.uiState,
                        onEvent: viewModel.handle(event:)
                    )
                }
            }
            .presentationDetents([.height(bottomSheetSize)])
            .presentationDragIndicator(.visible)
        }
        .onAppear(perform: viewModel.initData)
        .onChange(of: viewModel.uiEffect){
            observe(effect:viewModel.uiEffect)
        }
    }
    
    private func observe(effect: TransactionsEffect?) {
        if let sideEffect = effect {
            switch onEnum(of: sideEffect) {
            case .hideSheet: print("Hide sheet")
            case .navigateBack: navigation.navigateBack()
            case .toDetail(let effect):
                navigation.navigate(to: .transaction(transactionId: effect.id))
            case .showError(let effect):
                print(effect.message)
            case .openDrawer:
                print("open drawer")
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    TransactionsScreen()
}
