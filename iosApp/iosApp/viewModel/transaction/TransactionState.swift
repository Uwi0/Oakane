import Foundation
import Shared

struct TransactionState {
    var transaction: TransactionModel = dummyValue()
    
    init(){}
    
    init(state: TransactionStateKt){
        transaction = state.transaction
    }
}
