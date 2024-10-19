import Foundation
import Shared


final class TransactionsViewModel: ObservableObject {
    
    @Published var searchQuery: String = ""
    @Published var transactionType: TransactionType? = nil
    @Published var dateCreated: Int64 = 0
    
    @Published var transactions: [TransactionModel] = []
    
    private var viewModel: TransactionViewModelAdapter = Koin.instance.get()
    
    init () {
        viewModel.observeState { [weak self] transactions in
            DispatchQueue.main.async {
                self?.transactions = transactions
            }
        }
    }
    
    func filterBy(query: String){
        viewModel.filterTransactionsBy(query: query, type: transactionType, selectedDate: dateCreated)
    }
    
    func filterBy(type: TransactionType){
        viewModel.filterTransactionsBy(query: searchQuery, type: type, selectedDate: dateCreated)
    }
    
    func filterBy(date: Int64){
        viewModel.filterTransactionsBy(query: searchQuery, type: transactionType, selectedDate: date)
    }
    
}
