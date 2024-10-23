import Foundation
import Shared

class TransactionViewModel: ObservableObject {
    @Published var transactions: TransactionModel = TransactionModelKt.dummyValue()
    
    private var viewModel: TransactionViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] transactions in
            DispatchQueue.main.async {
                self?.transactions = transactions
            }
        }
    }
    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(transactionId: transactionId)
    }
}
