import Foundation
import Shared


final class TransactionsViewModel: ObservableObject {
    
    @Published var searchQuery: String = ""
    @Published var transactionType: String = ""
    @Published var dateCreated: Int64 = 0
    @Published var bottomSheetContent: TransactionsUiEvent = .TransactionType
    @Published var showBottomSheet: Bool = false
    
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
    
    func filterBy(type: String){
        viewModel.filterTransactionsBy(query: searchQuery, type: type, selectedDate: dateCreated)
    }
    
    func filterBy(date: Int64){
        viewModel.filterTransactionsBy(query: searchQuery, type: transactionType, selectedDate: date)
    }
    
    func onShowBottomSheet(uiEvent: TransactionsUiEvent){
        bottomSheetContent = uiEvent
        showBottomSheet = true
    }
    
    func hideBottomSheet(){
        showBottomSheet = false
    }
    
    func onSelected(date: Int64){
        showBottomSheet = false
        dateCreated = date
    }
    
    func deleteTransacion(item: IndexSet){
        item.forEach { index in viewModel.deleteTransaction(item: transactions[index]) }
    }
}
