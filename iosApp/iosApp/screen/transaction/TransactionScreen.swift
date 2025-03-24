import SwiftUI
import Shared

struct TransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel: TransactionViewModel = TransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private let toolbarContent = ToolBarContent(title: "Transaction", action1: "square.and.pencil", action2: "trash")
    
    private var uiState: TransactionState {
        viewModel.uiState
    }
    
    var body: some View {
        VStack{
            NavigationTopAppBarView()
            VStack(spacing: 16) {
                TransactionTopContentView(transaction: uiState.transaction)
                TransactionDetailContentView(state: uiState)
                TransactionNoteView(note: uiState.transaction.note)
                if uiState.transaction.imageFileName != "" {
                    TransactionImagePreView(
                        imageUrl: uiState.transaction.imageFileName,
                        showDismissButton: false
                    )
                }
                Spacer()
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .background(ColorTheme.surface.ignoresSafeArea())
        .frame(maxHeight: .infinity, alignment: .top)
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect){
            observe(effect:viewModel.uiEffect)
        }
        .onAppear {
            viewModel.initializeData(transactionId: transactionId)
        }
    }
    
    @ViewBuilder
    private func NavigationTopAppBarView() -> some View {
        VStack{
            NavigationTopAppbar(
                title: "Transaction",
                actionContent: {
                    Image(systemName: "pencil")
                        .frame(width: 24, height: 24)
                        .onTapGesture { viewModel.handle(event: .EditTransaction()) }
                    
                    Image(systemName: "trash")
                        .frame(width: 24, height: 24)
                        .onTapGesture { viewModel.handle(event: .DeleteTransaction()) }
                },
                onAction: { navigation.navigateBack() }
            )
            Divider()
        }
    }
    
    private func observe(effect: TransactionEffect?){
        if let safeEffect = effect {
            switch onEnum(of: safeEffect) {
            case .editTransactionBy(let transactionEffect):
                navigation.navigate(to: .addTransaction(transactionId: transactionEffect.id))
            case .navigateBack:
                navigation.navigateBack()
            case .showError(let error):
                print(error.message)
            }
        }
        viewModel.uiEffect = nil
    }
    
}

#Preview {
    TransactionScreen(transactionId: 0)
}
