import Foundation
import Shared

struct TransactionsState {
    var transactions: [TransactionModel] = []
    var categories: [CategoryModel] = []
    var searchQuery: String = ""
    var selectedType: TransactionType? = nil
    var selectedDate: Int64 = 0
    var selectedCategory: CategoryModel? = nil
    var sheetShown: Bool = false
    var sheetContent: TransactionsContentSheet = .type
    
    init(){}
    
    init(state: TransactionsStateKt){
        transactions = state.filteredTransactions
        categories = state.categories
        searchQuery = state.searchQuery
        selectedType = state.selectedType
        selectedDate = state.selectedDate
        selectedCategory = state.selectedCategory
        sheetShown = state.sheetShown
        sheetContent = state.sheetContent
    }
}
