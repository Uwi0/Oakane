import SwiftUI

struct WalletsScreen: View {
    
    @StateObject var walletsViewModel: WalletsViewModel = WalletsViewModel()
    
    private var uiState: WalletsState { walletsViewModel.uiState }
    
    var body: some View {
        Text("WalletsScreen \(uiState.wallets.count)")
    }
}

#Preview {
    WalletsScreen()
}
