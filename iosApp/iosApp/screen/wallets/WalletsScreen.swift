import SwiftUI

struct WalletsScreen: View {
    
    @StateObject var walletsViewModel: WalletsViewModel = WalletsViewModel()
    
    private var uiState: WalletsState { walletsViewModel.uiState }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                WalletsTopAppBar()
                WalletsContentView(walletItems: uiState.wallets)
            }
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: {}
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
