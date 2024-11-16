import Foundation
import Shared

struct TransactionsState {
    var transactions: [TransactionModel] = []
    var searchQuery: String = ""
    var selectedType: TransactionType? = nil
    var selectedDate: Int64 = 0
    var selectedCategory: CategoryModel? = nil
    var sheetSown: Bool = false
    var sheetContent: TransactionsContentSheet = .type
}
