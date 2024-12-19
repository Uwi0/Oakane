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
                    case .create: CreateWalletSheetView(uiState: uiState)
                    case .selectColor: Text("Select Color")
                    case .selectCurrency: Text("Select Currency")
                    case .selectIcon: Text("Select Icon")
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
