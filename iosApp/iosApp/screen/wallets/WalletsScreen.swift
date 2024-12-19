import SwiftUI

struct WalletsScreen: View {
    
    @StateObject var viewModel: WalletsViewModel = WalletsViewModel()
    
    private var uiState: WalletsState { viewModel.uiState }
    
    private var bottomSheetSize: PresentationDetent {
        switch uiState.sheetContent {
        case .create: return .fraction(0.7)
        case .selectIcon: return .fraction(0.9)
        case .selectColor: return .large
        case .selectCurrency: return .large
        }
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletsTopAppBar()
                WalletsContentView(walletItems: uiState.wallets)
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
    }
}

struct WalletsTopAppBar: View{
    
    @State private var searchQuery: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(title: "Wallets", navigateBack: {})
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Wallet...")
                .padding(.horizontal, 16)
            Divider()
        }
    }
}

#Preview {
    WalletsScreen()
}
