import Foundation
import Shared

struct WalletsState {
    var wallets: [WalletItemModel] = []
    var sheetShown: Bool = false
    var sheetContent: WalletSheetContent = .create
    var walletName: String = ""
    var imageFile: String = ""
    var selectedIcon: CategoryIconName = .wallet
    var selectedColor: Int32 = 0
    var colors: [String] = []
    
    init(){}
    
    init (walletsState: WalletsStateKt) {
        self.wallets = walletsState.wallets
        self.sheetShown = walletsState.isSheetShown
        self.sheetContent = walletsState.sheetContent
        self.walletName = walletsState.walletName
        self.imageFile = walletsState.imageFile
        self.selectedIcon = walletsState.selectedIcon
        self.selectedColor = walletsState.defaultColor
        self.colors = walletsState.colors
    }
}
