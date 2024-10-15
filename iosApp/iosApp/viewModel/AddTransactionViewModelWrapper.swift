import Foundation
import Shared

final class AddTRansactionViewModelWrapper: ObservableObject {
    private let addTransactionViewModel: AddTransactionViewModel = Koin.instance.get()
    
    func save(transaction: TransactionParam){
        print("\(transaction.title)")
        addTransactionViewModel.save(transaction: transaction)
    }
}
