import SwiftUI
import Shared

struct CreateWalletSheetView: View {
    let uiState: WalletsState
    var body: some View {
        VStack(spacing: 16) {
            NameAndIconContentView(
                title: "Create Wallet",
                imageName: uiState.imageFile,
                icon: uiState.selectedIcon,
                color: uiState.selectedColor
            )
        }
        .padding(.horizontal, 16)
        .padding(.top, 16)
        .padding(.bottom, 24)
    }
}

fileprivate struct NameAndIconContentView: View {
    let title: String
    let imageName: String
    let icon: CategoryIconName
    let color: Int32
    
    @State private var walletName: String = ""
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(Typography.titleMedium)
            HStack(spacing: 8) {
                SelectedIconView(imageName: imageName, icon: icon, color: color)
                OutlinedTextFieldView(value: $walletName, placeHolder: "Wallet Name", onValueChange: { value in })
            }
        }
    }
}
