import SwiftUI
import Shared

struct WalletsScreen: View {
    
    @StateObject private var viewModel: WalletsViewModel = WalletsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: WalletsState { viewModel.uiState }
    
    private var bottomSheetSize: PresentationDetent {
        switch uiState.sheetContent {
        case .create: return .fraction(0.50)
        case .selectIcon: return .fraction(0.9)
        case .selectColor: return .large
        }
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletsTopAppBar(onNavigateBack: {viewModel.handle(event: .NavigateBack())})
                WalletsContentView(walletItems: uiState.wallets, onEvent: viewModel.handle(event:))
            }
            .sheet(
                isPresented: $viewModel.uiState.sheetShown,
                onDismiss: { }
            ){
                WalletsSheetContent()
                    .presentationDetents([bottomSheetSize])
                    .presentationDragIndicator(.visible)
            }
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: { }
            )
        }
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect) {
            observe(effect: viewModel.uiEffect)
        }
    }
    
    private func observe(effect: WalletsEffect?){
        if let safeEffect = effect {
            switch onEnum(of: safeEffect){
            case .dismissBottomSheet:
                print("dismiss")
            case .navigateBack:
                navigation.navigateBack()
            case .showError(let effect):
                print("error \(effect.message)")
            case .navigateToWallet(let effect):
                print("Navigate to wallet")
            }
        }
        viewModel.uiEffect = nil
    }
    
    @ViewBuilder
    private func WalletsSheetContent() -> some View {
        VStack {
            switch uiState.sheetContent {
            case .create: CreateWalletSheetContent()
            case .selectColor: Text("Select Color")
            case .selectIcon: SelectIconView(
                selectedIcon: uiState.selectedIcon,
                selectedColor: uiState.selectedColor,
                onPickIcon: { icon in },
                onTakImage: { file in },
                onConfirm: {}
            )
            }
        }
    }
    
    @ViewBuilder
    private func CreateWalletSheetContent() -> some View {
        let onEvent: (CreateWalletEvent) -> Void = { walletEvent in
            
        }
        
        CreateWalletSheetView(
            imageFile: uiState.imageFile,
            selectedIcon: uiState.selectedIcon,
            selectedColor: uiState.selectedColor,
            walletName: $viewModel.uiState.walletName,
            startBalance: $viewModel.uiState.startBalance,
            colors: uiState.colors,
            onEvent: onEvent
        )
    }
}

struct WalletsTopAppBar: View{
    
    let onNavigateBack: () -> Void
    @State private var searchQuery: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(title: "Wallets", navigateBack: onNavigateBack)
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Wallet...")
                .padding(.horizontal, 16)
            Divider()
        }
    }
}

#Preview {
    WalletsScreen()
}
