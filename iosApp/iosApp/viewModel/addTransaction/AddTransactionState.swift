import Foundation
import Shared

struct AddTransactionState {
    var title = ""
    var amount: Int = 0
    var transactionType: TransactionType = .income
    var category: CategoryModel = defaultCategoryModel
    var selectedDate: Int64 = 0
    var note: String = ""
    var showSheet: Bool = false
    var categories: [CategoryModel] = []
    
    init(){}
    
    init(state: AddTransactionStateKt){
        title = state.title
        amount = Int(state.amount)
        transactionType = state.transactionType
        category = state.category
        selectedDate = state.date
        note = state.note
        showSheet = state.sheetShown
        categories = state.categories
    }
}
