import Foundation
import Shared

struct AddTransactionState {
    var title = ""
    var amount: Int = 0
    var selectedType: String = TransactionType.income.name
    var category: CategoryModel = defaultCategoryModel
    var selectedDate: Int64 = 0
    var note: String = ""
    var showSheet: Bool = false
    var categories: [CategoryModel] = []
    var imageFile: String = ""
    
    init(){}
    
    init(state: AddTransactionStateKt){
        title = state.title
        amount = Int(state.amount)
        selectedType = state.transactionType.name
        category = state.category
        selectedDate = state.date
        note = state.note
        showSheet = state.sheetShown
        categories = state.categories
        imageFile = state.imageFileName
    }
}
