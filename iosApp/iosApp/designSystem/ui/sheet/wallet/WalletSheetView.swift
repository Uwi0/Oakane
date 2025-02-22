import SwiftUI

struct WalletSheetView: View {
    
    @ObservedObject var state: WalletSheetState
    
    var body: some View {
        switch state.content {
        case .wallet: AddWalletView()
        case .icon: SelectIconView()
        }
    }
    
    @ViewBuilder
    private func AddWalletView() -> some View {
        CreateWalletSheetView(state: state)
    }
    
    @ViewBuilder
    private func SelectIconView() -> some View {
        Text("icon view")
    }
}

