import Foundation
import Shared

struct AddTransactionState {
    var title: String = ""
    var amount: String = ""
    var note: String = ""
    var transactionOptions: [String] = TransactionType.entries.map { $0.name }
    var selectedTransactionOption: String = TransactionType.income.name
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
    
    func getTransaction() -> TransactionParam {
        return TransactionParam(
            title: title,
            amount: Double(amount) ?? 0.0,
            type: TransactionTypeKt.asLong(selectedTransactionOption),
            category: selectedCategoryOption,
            dateCreated: Int64(selectedDate.timeIntervalSince1970),
            note: note
        )
    }
}
