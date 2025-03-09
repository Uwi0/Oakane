import SwiftUI
import Shared

struct WalletsScreen: View {
    
    @Binding var openDrawer: Bool
    var showDrawer: Bool = false
    @StateObject private var viewModel: WalletsViewModel = WalletsViewModel()
    @StateObject private var sheetState: WalletSheetState = WalletSheetState()
    @EnvironmentObject private var nav: AppNavigation
    @State private var searchQuery: String = ""
    
    private var uiState: WalletsState { viewModel.uiState }

    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletsTopAppBar()
                WalletsContentView()
            }
            .dynamicHeightSheet(
                isPresented: Binding(
                    get: { uiState.isSheetShown },
                    set: { viewModel.handle(event: .ShowSheet(shown: $0)) }
                ),
                content: {
                    WalletSheetView(state: sheetState)
                }
            )
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: { viewModel.handle(event: .ShowSheet(shown: true)) }
            )
        }
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect) {
            observe(effect: viewModel.uiEffect)
        }
        .onAppear {
            sheetState.onSaveWallet = { wallet in
                viewModel.handle(event: .SaveWallet(wallet: wallet))
            }
        }
    }
    
    @ViewBuilder
    private func WalletsContentView() -> some View {
        ScrollView {
            VStack {
                WalletItemsView()
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 16)
        }
        .scrollIndicators(.hidden)
    }
    
    @ViewBuilder
    private func WalletItemsView() -> some View {
        ForEach(uiState.wallets, id: \.self){ walletItem in
            WalletItemView(
                wallet: walletItem,
                onSelectWallet: {
                    viewModel.handle(event: .SelectPrimaryWalletBy(id: walletItem.id))
                },
                navigateToDetails: {
                    viewModel.handle(event: .ClickedWallet(item: walletItem))
                }
            )
        }
    }
    
    @ViewBuilder
    private func WalletsTopAppBar() -> some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(
                title: "Wallets",
                showDrawer: showDrawer,
                onAction: onAction
            )
            OutlinedSearchTextFieldView(
                query: $searchQuery,
                placeHolder: "Search Wallet..."
            )
            .padding(.horizontal, 16)
            Divider()
        }
    }
    
    private func onAction() {
        if showDrawer {
            viewModel.handle(event: .OpenDrawer())
        } else {
            viewModel.handle(event: .NavigateBack())
        }
    }
    
    private func observe(effect: WalletsEffect?){
        guard let effect else { return }
        
        switch onEnum(of: effect){
        case .dismissBottomSheet: sheetState.resetContent()
        case .navigateBack: nav.navigateBack()
        case .showError(let effect): print("error \(effect.message)")
        case .navigateToWallet(let effect): nav.navigate(to: .wallet(id: effect.id))
        case .openDrawer: openDrawer = !openDrawer
        }
        
        viewModel.uiEffect = nil
    }
    
    private func capturedEventSave(wallet: WalletModel) {
        viewModel.handle(event: .SaveWallet(wallet: wallet))
    }

}


#Preview {
    WalletsScreen(openDrawer: .constant(false))
        .environmentObject(AppNavigation())
}
