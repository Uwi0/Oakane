import Foundation
import Shared


final class TransactionsViewModel: ObservableObject {
    @Published var transactions: [TransactionModel] = []
    
    private var viewModel: TransactionViewModelAdapter = Koin.instance.get()
    
    init () {
        viewModel.observeState { [weak self] transactions in
            DispatchQueue.main.async {
                self?.transactions = transactions
            }
        }
    }
    
}
