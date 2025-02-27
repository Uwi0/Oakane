import SwiftUI
import Shared

struct WalletScreen: View {
    
    let walletId: Int64
    
    @StateObject private var viewModel: WalletViewModel = WalletViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: WalletState {
        viewModel.uiState
    }
    
    private var filterColorIndicator: Color {
        ColorTheme.outline
    }
    
    private var currency: Currency {
        uiState.wallet.currency
    }
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletTopbar()
                WalletContent()
                LogViews()
            }
            
            if uiState.dialogVisible {
                WalletDialogView(uiState: uiState, onEvent: viewModel.handle)
            }
        }
        .navigationBarBackButtonHidden(true)
        .task {
            viewModel.iniData(walletId: walletId)
        }
    }
    
    @ViewBuilder
    private func WalletTopbar() -> some View {
        let dialogContent = WalletDialogContent.deleteWallet
        VStack {
            NavigationTopAppbar(
                title: "Wallet",
                actionContent: {
                    BarAction(systemName: "pencil")
                    Spacer().frame(width: 16)
                    BarAction(systemName: "trash").onTapGesture {
                        viewModel.handle(event: .ShowDialog(content: dialogContent, shown: true))
                    }
                },
                navigateBack: { navigation.navigateBack() }
            )
            Divider()
        }
    }
    
    @ViewBuilder
    private func BarAction(systemName: String) -> some View {
        Image(systemName: systemName)
            .resizable()
            .frame(width: 24, height: 24)
    }
    
    @ViewBuilder
    private func WalletContent() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            WalletDetailItemView(state: uiState)
            CardNoteView(note: uiState.wallet.note)
            FilterLogView()
        }
        .padding(.horizontal, 16)
        .padding(.top, 24)
    }
    
    @ViewBuilder
    private func FilterLogView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Wallet History").font(Typography.titleMedium)
            FilterLogComponent()
        }
    }
    
    @ViewBuilder
    private func FilterLogComponent() -> some View {
        HStack(alignment: .center, spacing: 8) {
            OutlinedSearchTextFieldView(
                query: Binding(
                    get: { uiState.searchQuery },
                    set: { query in  viewModel.handle(event: .SearchLog(query: query)) }
                ),
                placeHolder: "Search"
            )
            Image(systemName: "line.3.horizontal.decrease")
                .resizable()
                .scaledToFit()
                .foregroundStyle(filterColorIndicator)
                .frame(width: 24, height: 24)
        }
    }
    
    @ViewBuilder
    private func LogViews() -> some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 8) {
                LogItems()
            }
        }
        .scrollIndicators(.hidden)
        .padding(.top, 16)
    }
    
    @ViewBuilder
    private func LogItems() -> some View {
        ForEach(uiState.filteredLogItems, id: \.id) { item in
            let type = onEnum(of: item)
            switch type {
            case .goalSavingLogItem(let goal): GoalSavingItem(log: goal)
            case .transactionLogItem(let transaction): TransactionItem(log: transaction)
            case .walletTransferLogItem(let wallet): TransferLogItemView(item: wallet.data)
            }
        }
    }
    
    @ViewBuilder
    private func GoalSavingItem(log: WalletLogItemGoalSavingLogItem) -> some View {
        GoalSavingItemView(item: log.data,currency: currency,isInLog: true)
    }
    
    @ViewBuilder
    private func TransactionItem(log: WalletLogItemTransactionLogItem) -> some View {
        TransactionItemView(transaction: log.data)
    }
}

#Preview {
    WalletScreen(walletId: 0)
}
