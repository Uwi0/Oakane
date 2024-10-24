import Foundation
import Shared

final class TransactionViewModel: ObservableObject {
    @Published var transaction: TransactionModel = TransactionModelKt.dummyValue()
    
    private var viewModel: TransactionViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] transactions in
            DispatchQueue.main.async {
                self?.transaction = transactions
            }
        }
    }
    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(transactionId: transactionId)
    }
    
    func deletTransaction(transactionId: Int64) {
        viewModel.deleteTransactionBy(id: transactionId)
    }
}
