import SwiftUI

struct TransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel: TransactionViewModel = TransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private let toolbarContent = ToolBarContent(title: "Transaction", action1: "square.and.pencil", action2: "trash")
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack(spacing: 16) {
                TransactionTopContentView(transaction: viewModel.transaction)
                TransactionDetailContentView(transaction: viewModel.transaction)
                TransactionNoteView(note: viewModel.transaction.note)
                Text("Add Another feature soon!")
            }
            .frame(maxHeight: .infinity, alignment: .top)
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .navigationBarBackButtonHidden(true)
        .customToolbar(content: toolbarContent, onEvent: onEvent)
        .onAppear {
            viewModel.initializeData(transactionId: transactionId)
        }
    }
    
    private func onEvent(_ event: ToolbarEvent) {
        navigation.navigateBack()
    }
    
}

