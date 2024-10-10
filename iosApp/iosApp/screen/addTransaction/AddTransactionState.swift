import Foundation
import Shared

struct AddTransactionState {
    var title: String = ""
    var amount: String = ""
    var note: String = ""
    var transactionOptions: [String] = TransactionType.entries.map { $0.asString() }
    var selectedTransactionOption: String = TransactionType.income.asString()
    var categoryOptions = [
        "Food & Dining",
        "Transportation",
        "Entertainment",
        "Utilities",
        "Health & Fitness"
    ]
    var selectedCategoryOption: String = "Food & Dining"
    var showDatePicker: Bool = false
    var selectedDate: Date = Date()
}

extension TransactionType {
    func asString() -> String {
        switch self {
        case .income:
            return "Income"
        case .expense:
            return "Expense"
        default:
            return ""
        }
    }
}
