import SwiftUI
import Shared

struct CreateWalletSheetView: View {
    
    @ObservedObject var state: WalletSheetState
    
    var body: some View {
        VStack(spacing: 16) {
            NameAndIconContentView()
            StartWithBalanceContentView()
            HorizontalColorSelectorView(colors: state.defaultColors, selectedColor: $state.selectedColor)
            Spacer()
            FilledButtonView(text: "Add Wallet", onClick: { state.saveWallet() } ).frame(height: 48)
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
    }
    
    @ViewBuilder
    private func NameAndIconContentView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Create Wallet")
                .font(Typography.titleMedium)
            
            HStack(alignment: .center, spacing: 8) {
                SelectedIconView(imageName: state.imageFile, icon: state.selectedIcon, color: state.selectedColor)
                    .onTapGesture { state.content = .icon }
                
                OutlinedTextFieldView(
                    value: $state.walletName,
                    placeHolder: "Wallet Name",
                    showLabel: false
                )
            }
        }
    }
    
    @ViewBuilder
    private func StartWithBalanceContentView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Start Balance")
                .font(Typography.titleMedium)
            
            OutlinedCurrencyTextFieldView(
                value: $state.startBalance,
                onValueChange: { _ in }
            )
        }
    }
        
}

enum CreateWalletEvent {
    case selectedIcon
    case selectWallet(color: String)
    case saveWallet
}


