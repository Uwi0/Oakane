import Foundation
import SwiftUI
import Shared

enum WalletSheetContent {
    case wallet
    case icon
}

class WalletSheetState: ObservableObject {
    @Published var content: WalletSheetContent = .wallet
    @Published var walletName: String = ""
    @Published var startBalance: Int = 0
    @Published var selectedColor: Color = .green
    @Published var imageFile: String = ""
    @Published var selectedIcon: CategoryIconName = .wallet
    var id: Int64 = 0
    var currency: Currency = .usd
    var onSaveWallet : (WalletModel) -> Void = { _ in }
    
    let defaultColors = colorsSelector.map{ color in Color(hex: color.toColorLong()) }
    
    func initData(wallet: WalletItemModel) {
        self.id = wallet.id
        self.currency = wallet.currency
        self.walletName = wallet.name
        self.startBalance = Int(wallet.balance)
        self.selectedColor = Color(hex: wallet.color.toColorLong())
        self.imageFile = wallet.icon
        self.selectedIcon = wallet.iconName
    }
    
    func resetContent() {
        content = .wallet
    }
    
    func updateImage(_ image: String) {
        DispatchQueue.main.async {
            self.content = .wallet
            self.imageFile = image
        }
    }
    
    func saveWallet() {
        let walletModel = WalletModel(
            id: id,
            currency: currency,
            balance: Double(startBalance),
            name: walletName,
            isDefaultIcon: imageFile.isEmpty ? true : false,
            icon: imageFile.isEmpty ? selectedIcon.displayName : imageFile,
            color: selectedColor.toHexString() ?? "0xFF4CAF50",
            note: "-"
        )
        onSaveWallet(walletModel)
    }
}
