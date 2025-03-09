import SwiftUI
import Shared

struct TransactionsScreen: View {
    
    @Binding var openDrawer: Bool
    var showDrawer: Bool = false
    @StateObject private var viewModel = TransactionsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: TransactionsState {
        viewModel.uiState
    }
    
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
                    showDrawer: showDrawer,
                    onEvent: viewModel.handle(event:)
                )
                
                TransactionsView(
                    transactions: viewModel.uiState.transactions,
                    onEvent: viewModel.handle(event:)
                )
            }

        }
        .navigationBarBackButtonHidden(true)
        .sheet(
            isPresented: Binding(
                get: { uiState.sheetShown },
                set: { shown in viewModel.handle(event: .ShowSheet(shown: shown, content: .type)) }
            )
        ){
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
            case .toDetail(let effect): navigation.navigate(to: .transaction(id: effect.id))
            case .showError(let effect): print(effect.message)
            case .openDrawer: openDrawer = !openDrawer
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    TransactionsScreen(openDrawer: .constant(false))
}
