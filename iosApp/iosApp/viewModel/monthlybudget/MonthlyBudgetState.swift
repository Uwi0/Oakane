import Foundation
import Shared

struct MonthlyBudgetState {
    var isEditMode: Bool = false
    var amount: Int = 0
    var isDialogShown: Bool = false
    var categoryLimits: [CategoryLimitModel] = []
    var expenseCategories: [CategoryModel] = []
}
