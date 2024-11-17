import Foundation
import Shared

struct AddTransactionState {
    var title = ""
    var amount: String = ""
    var transactionType: TransactionType = .income
    var category: CategoryModel = defaultCategoryModel
    var selectedDate: Int64 = 0
    var note: String = ""
    var showSheet: Bool = false
    var categories: [CategoryModel] = []
}
