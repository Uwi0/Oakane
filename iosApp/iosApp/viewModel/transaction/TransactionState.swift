import Foundation
import Shared

struct TransactionState {
    var transaction: TransactionModel = dummyValue()
    var wallet: WalletModel = defaultWallet
    var currency: Currency = .idr
    
    init(){}
    
    init(state: TransactionStateKt){
        transaction = state.transaction
        wallet = state.wallet
        currency = state.currency
    }
}
