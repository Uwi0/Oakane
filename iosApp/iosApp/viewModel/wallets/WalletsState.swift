import Foundation
import Shared

struct WalletsState {
    var wallets: [WalletItemModel] = []
    var sheetShown: Bool = false
    
    init(){}
    
    init (walletsState: WalletsStateKt) {
        self.wallets = walletsState.wallets
        self.sheetShown = walletsState.isSheetShown
    }
}
