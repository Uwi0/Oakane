import Foundation
import Shared

struct MonthlyBudgetState {
    var isEditMode: Bool = false
    var amount: Int = 0
    var isDialogShown: Bool = false
    var categoryLimits: [CategoryLimitModel] = []
    var expenseCategories: [CategoryModel] = []
    var categoryLimit: CategoryLimitModel? = nil
    
    init(){}
    
    init(state: MonthlyBudgetStateKt){
        isEditMode = state.isEditMode
        amount = Int(state.realAmount)
        isDialogShown = state.dialogShown
        categoryLimits = state.categoryLimits
        expenseCategories = state.expenseCategories
        categoryLimit = state.selectedCategoryLimit
    }
}
