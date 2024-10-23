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
                HStack {
                    Image(ImageConstants.fubukiStare)
                        .resizable()
                        .frame(width: 48, height: 48)
                        .clipShape(Circle())
                        .scaledToFit()
                    Spacer()
                    VStack(alignment: .trailing) {
                        Text(viewModel.transactions.title)
                            .font(Typography.titleSmall)
                        Text("Rp: \(viewModel.transactions.amount.formatted())")
                            .font(Typography.titleMedium)
                    }
                }
                .customBackground(backgroundColor: ColorTheme.surface)
                Spacer()
            }
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

