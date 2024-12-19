import SwiftUI
import Shared

struct CreateWalletSheetView: View {
    
    let uiState: WalletsState
    let onEvent: (WalletsEvent) -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            NameAndIconContentView(
                imageName: uiState.imageFile,
                icon: uiState.selectedIcon,
                color: uiState.selectedColor,
                onEvent: onEvent
            )
            StartWithBalanceContentView(onEvent: onEvent)
            CurrencyContentView()
            HorizontalColorSelectorView(
                selectedColor: Color(hex: uiState.selectedColor),
                colors: uiState.colors,
                onSelectedColor: { hex in onEvent(.SelectWallet(color: hex))}
            )
            Spacer()
            FilledButtonView(text: "Add Wallet", onClick: { onEvent(.SaveWallet())})
                .frame(height: 48)
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
    }
}

fileprivate struct NameAndIconContentView: View {
    let imageName: String
    let icon: CategoryIconName
    let color: Int32
    let onEvent: (WalletsEvent) -> Void
    
    @State private var walletName: String = ""
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Create Wallet")
                .font(Typography.titleMedium)
            HStack(alignment: .center, spacing: 8) {
                SelectedIconView(imageName: imageName, icon: icon, color: color)
                    .onTapGesture { onEvent(.SelectedSheet(content: .selectIcon)) }
                OutlinedTextFieldView(
                    value: $walletName,
                    placeHolder: "Wallet Name",
                    showLabel: false,
                    onValueChange: { value in onEvent(.OnChangeWallet(name: value))}
                )
            }
        }
    }
}

fileprivate struct StartWithBalanceContentView: View {
    
    let onEvent: (WalletsEvent) -> Void
    @State private var value: Int = 0
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Start Balance")
                .font(Typography.titleMedium)
            OutlinedCurrencyTextFieldView(
                value: $value,
                onValueChange: { value in onEvent(.ChangeStart(balance: value))}
            )
        }
    }
}

fileprivate struct CurrencyContentView: View {
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Currency")
                .font(Typography.titleMedium)
            Button(action: {}){
                HStack {
                    Text("IDR")
                        .font(Typography.bodyLarge)
                    Spacer()
                    Image(systemName: "chevron.right")
                }
                .padding(16)
                .frame(maxWidth: .infinity)
                .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.outline, lineWidth: 2))
            }
            .buttonStyle(PlainButtonStyle())
        }
    }
}
