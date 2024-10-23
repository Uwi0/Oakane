import SwiftUI

struct TransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel: TransactionViewModel = TransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private let toolbarContent = ToolBarContent(title: "Transaction", action1: "square.and.pencil", action2: "trash")
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                
            }
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

