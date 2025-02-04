import Foundation
import Shared

struct WalletsState {
    var wallets: [WalletItemModel] = []
    var sheetShown: Bool = false
    var sheetContent: WalletSheetContent = .create
    var walletName: String = ""
    var imageFile: String = ""
    var selectedIcon: CategoryIconName = .wallet
    var selectedColor: Int64 = 0
    var colors: [String] = []
    var startBalance: Int = 0
    
    init(){}
    
    init (walletsState: WalletsStateKt) {
        self.wallets = walletsState.wallets
        self.sheetShown = walletsState.isSheetShown
        self.colors = walletsState.colors

    }
}
