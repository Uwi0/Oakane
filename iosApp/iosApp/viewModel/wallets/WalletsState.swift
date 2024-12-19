import Foundation
import Shared

struct WalletsState {
    var wallets: [WalletItemModel] = []
    var sheetShown: Bool = false
    var sheetContent: WalletSheetContent = .create
    
    init(){}
    
    init (walletsState: WalletsStateKt) {
        self.wallets = walletsState.wallets
        self.sheetShown = walletsState.isSheetShown
        self.sheetContent = walletsState.sheetContent
    }
}
