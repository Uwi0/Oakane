import SwiftUI
import Shared

struct TransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel: TransactionViewModel = TransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private let toolbarContent = ToolBarContent(title: "Transaction", action1: "square.and.pencil", action2: "trash")
    
    private var transaction: TransactionModel {
        viewModel.uiState.transaction
    }
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                AddTransactionTopBarView(
                    title: "Transaction",
                    onNavigateBack: { viewModel.handle(event: .NavigateBack())},
                    onEdit: { viewModel.handle(event: .EditTransaction())},
                    onDelete: { viewModel.handle(event: .DeleteTransaction())}
                )
                VStack(spacing: 16) {
                    TransactionTopContentView(transaction: transaction)
                    TransactionDetailContentView(transaction: transaction)
                    TransactionNoteView(note: transaction.note)
                    Text("Add Another feature soon!")
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
            }
            .frame(maxHeight: .infinity, alignment: .top)
            
        }
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect, perform: observe(effect:))
        .onAppear {
            viewModel.initializeData(transactionId: transactionId)
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

struct AddTransactionTopBarView: View {
    
    let title: String
    let onNavigateBack: () -> Void
    let onEdit: () -> Void
    let onDelete: () -> Void
    
    var body: some View {
        VStack{
            NavigationTopAppbar(
                title: title,
                actionContent: {
                    Image(systemName: "pencil")
                        .frame(width: 24, height: 24)
                        .onTapGesture { onEdit() }
                    
                    Image(systemName: "trash")
                        .frame(width: 24, height: 24)
                        .onTapGesture { onDelete()
                        }
                },
                navigateBack: onNavigateBack
            )
            Divider()
        }
    }
}

