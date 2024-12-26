import SwiftUI
import Shared

struct WalletsScreen: View {
    
    @StateObject private var viewModel: WalletsViewModel = WalletsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: WalletsState { viewModel.uiState }
    
    private var bottomSheetSize: PresentationDetent {
        switch uiState.sheetContent {
        case .create: return .fraction(0.65)
        case .selectIcon: return .fraction(0.9)
        case .selectColor: return .large
        case .selectCurrency: return .large
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
                onDismiss: { viewModel.handle(event: .IsSheet(shown: false))}
            ){
                VStack {
                    switch uiState.sheetContent {
                    case .create: CreateWalletSheetView(uiState: uiState, onEvent: viewModel.handle(event:))
                    case .selectColor: Text("Select Color")
                    case .selectCurrency: Text("Select Currency")
                    case .selectIcon: SelectIconView(
                        selectedIcon: uiState.selectedIcon,
                        selectedColor: uiState.selectedColor,
                        onPickIcon: { icon in viewModel.handle(event: .SelectedIcon(name: icon)) },
                        onTakImage: { file in viewModel.handle(event: .SelectedImage(file: file)) },
                        onConfirm: { viewModel.handle(event: .ConfirmIcon()) }
                    )
                    }
                }
                .presentationDetents([bottomSheetSize])
                .presentationDragIndicator(.visible)
            }
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: { viewModel.handle(event: .IsSheet(shown: true))}
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
            }
        }
        viewModel.uiEffect = nil
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
