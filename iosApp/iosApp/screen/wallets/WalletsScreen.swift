import SwiftUI
import Shared

struct WalletsScreen: View {
    
    @StateObject private var viewModel: WalletsViewModel = WalletsViewModel()
    @StateObject private var sheetState: WalletSheetState = WalletSheetState()
    @EnvironmentObject private var navigation: AppNavigation
    @State private var searchQuery: String = ""
    
    private var uiState: WalletsState { viewModel.uiState }
    
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletsTopAppBar()
                WalletContentView()
            }
            .dynamicHeightSheet(
                isPresented: Binding(
                    get: { uiState.sheetShown},
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
    }
    
    @ViewBuilder
    private func WalletContentView() -> some View {
        ScrollView {
            VStack {
                ForEach(uiState.wallets, id: \.self){ walletItem in
                    WalletItemView(
                        wallet: walletItem,
                        onSelectWallet: { },
                        navigateToDetails: { navigation.navigate(to: .wallet)}
                    )
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 16)
        }
        .scrollIndicators(.hidden)
    }
    
    private func observe(effect: WalletsEffect?){
        if let safeEffect = effect {
            switch onEnum(of: safeEffect){
            case .dismissBottomSheet:
                sheetState.resetContent()
            case .navigateBack:
                navigation.navigateBack()
            case .showError(let effect):
                print("error \(effect.message)")
            case .navigateToWallet(let effect):
                print("Navigate to wallet \(effect.id)")
            case .openDrawer:
                print("open drawer")
            }
        }
        viewModel.uiEffect = nil
    }
    
    @ViewBuilder
    private func WalletsTopAppBar() -> some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(title: "Wallets", navigateBack: navigation.navigateBack)
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Wallet...")
                .padding(.horizontal, 16)
            Divider()
        }
    }
}


#Preview {
    WalletsScreen()
        .environmentObject(AppNavigation())
}
