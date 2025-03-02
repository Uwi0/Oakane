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
    let id: Int64
    let currency: Currency
    var onSaveWallet : (WalletModel) -> Void = { _ in }
    
    init(wallet: WalletModel = defaultWallet) {
        self.id = wallet.id
        self.currency = wallet.currency
    }
    
    let defaultColors = colorsSelector.map{ color in Color(hex: color.toColorLong()) }
    
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
