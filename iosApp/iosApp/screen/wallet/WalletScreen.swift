import SwiftUI
import Shared

struct WalletScreen: View {
    
    let walletId: Int64
    
    @StateObject private var viewModel = WalletViewModel()
    @StateObject private var walletSheetState = WalletSheetState()
    @EnvironmentObject private var navigation: AppNavigation
    @State private var sheetID = UUID()
    
    private var uiState: WalletState {
        viewModel.uiState
    }
    
    private var filterColorIndicator: Color {
        uiState.hasFilter ? ColorTheme.primary : ColorTheme.outline
    }
    
    private var currency: Currency {
        uiState.wallet.currency
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletTopbar()
                WalletContent()
                LogViews()
            }
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: {
                    viewModel.handle(event: .ShowDialog(content: .moveBalance, shown: true))
                }
            )
            
            if uiState.dialogVisible {
                WalletDialogView(uiState: uiState, onEvent: viewModel.handle)
            }
        }
        .navigationBarBackButtonHidden(true)
        .sheet(
            isPresented: Binding(
                get: { uiState.isFilterSheetShown },
                set: { viewModel.handle(event: .ShowFilterSheet(shown: $0))}
            ),
            content: {
                FilterLogSheet(onEVent: viewModel.handle)
                    .presentationDetents([.height(480)])
            }
        )
        .task {
            viewModel.iniData(walletId: walletId)
        }
        .onChange(of: viewModel.uiEffect) {
            observeEffect(effect: viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func WalletTopbar() -> some View {
        let dialogContent = WalletDialogContent.deleteWallet
        VStack {
            NavigationTopAppbar(
                title: "Wallet",
                actionContent: {
                    BarAction(systemName: "pencil").onTapGesture {
                        viewModel.handle(event: .NavigateToCreateWallet())
                    }
                    Spacer().frame(width: 16)
                    BarAction(systemName: "trash").onTapGesture {
                        viewModel.handle(event: .ShowDialog(content: dialogContent, shown: true))
                    }
                },
                onAction: { viewModel.handle(event: .NavigateBack()) }
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
                .onTapGesture {
                    viewModel.handle(event: .ShowFilterSheet(shown: true))
                }
        }
    }
    
    @ViewBuilder
    private func LogViews() -> some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                LogItems()
            }
            .padding(.top, 16)
            .padding(.bottom, 24)
        }
        .scrollIndicators(.hidden)
        
    }
    
    @ViewBuilder
    private func LogItems() -> some View {
        ForEach(uiState.filteredLogItems, id: \.uniqueId) { item in
            let type = onEnum(of: item)
            switch type {
            case .goalSavingLogItem(let goal): GoalSavingItem(log: goal)
            case .transactionLogItem(let transaction): TransactionItem(log: transaction)
            case .walletTransferLogItem(let wallet): TransferLogItemView(item: wallet.data)
            }
        }
        .padding(.horizontal, 16)
    }
    
    @ViewBuilder
    private func GoalSavingItem(log: WalletLogItemGoalSavingLogItem) -> some View {
        GoalSavingItemView(item: log.data,currency: currency,isInLog: true)
    }
    
    @ViewBuilder
    private func TransactionItem(log: WalletLogItemTransactionLogItem) -> some View {
        TransactionItemView(transaction: log.data)
    }
    
    private func observeEffect(effect: WalletEffect?) {
        guard let effect = effect else { return }
        
        switch onEnum(of: effect) {
        case .navigateBack: navigation.navigateBack()
        case .navigateToCreateWallet: navigation.navigate(to: .createWallet(walletId: walletId))
        case .showError(let effect): print("error \(effect.message)")
        }
        
        viewModel.uiEffect = nil
    }
}

#Preview {
    WalletScreen(walletId: 0)
}
