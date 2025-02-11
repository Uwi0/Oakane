import SwiftUI
import Shared

struct CreateWalletSheetView: View {
    
    let imageFile: String
    let selectedIcon: CategoryIconName
    let selectedColor: Int64
    @Binding var walletName: String
    @Binding var startBalance: Int
    let colors: [String]
    let onEvent: (CreateWalletEvent) -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            NameAndIconContentView()
            StartWithBalanceContentView()
            HorizontalColorSelectorView(
                selectedColor: Color(hex: selectedColor),
                colors: colors,
                onSelectedColor: { hex in onEvent(.selectWallet(color: hex))}
            )
            Spacer()
            FilledButtonView(text: "Add Wallet", onClick: { onEvent(.saveWallet)})
                .frame(height: 48)
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
                SelectedIconView(imageName: imageFile, icon: selectedIcon, color: selectedColor)
                    .onTapGesture { onEvent(.selectedIcon) }
                
                OutlinedTextFieldView(
                    value: $walletName,
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
                value: $startBalance,
                onValueChange: { balance in onEvent(.changeStart(balance: balance))}
            )
        }
    }
        
}

enum CreateWalletEvent {
    case changeWallet(name: String)
    case selectedIcon
    case changeStart(balance: String)
    case selectWallet(color: String)
    case saveWallet
}


