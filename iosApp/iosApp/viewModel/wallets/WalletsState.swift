import Foundation
import Shared

struct WalletsState {
    var wallets: [WalletItemModel] = []
    
    init(){}
    
    init (walletsState: WalletsStateKt) {
        self.wallets = walletsState.wallets
    }
}
