import SwiftUI

struct WalletSheetView: View {
    
    @ObservedObject var state: WalletSheetState
    
    var body: some View {
        switch state.content {
        case .wallet: AddWalletSheetView()
        case .icon: SelectIconSheetView()
        }
    }
    
    @ViewBuilder
    private func AddWalletSheetView() -> some View {
        CreateWalletSheetView(state: state)
    }
    
    @ViewBuilder
    private func SelectIconSheetView() -> some View {
        SelectIconView(
            selectedIcon: $state.selectedIcon,
            selectedColor: state.selectedColor,
            onTakeImage: state.updateImage,
            onConfirm: { state.content = .wallet }
        )
    }
}

