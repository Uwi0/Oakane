import Foundation
import Shared

final class AddTRansactionViewModel: ObservableObject {
    
    @Published var title: String = ""
    @Published var amount: String = ""
    @Published var note: String = ""
    @Published var selectedTransactionOption: String = TransactionType.expense.name
    @Published var selectedCategoryOption: String = "Food & Dining"
    @Published var selectedDate: Date = Date()
    
    var categoryOptions = [
        "Food & Dining",
        "Transportation",
        "Entertainment",
        "Utilities",
        "Health & Fitness"
    ]
    var transactionOptions: [String] = TransactionType.allCases.map(\.name)
    var showDatePicker: Bool = false
    
    
    private let viewModel: AddTransactionViewModelAdapter = Koin.instance.get()
    private var transactionId: Int64 = 0
    
    init() {
        viewModel.observeState(onStateChange: { [weak self] state in
            DispatchQueue.main.async {
                if !state.title.isEmpty {
                    self?.title = state.title
                    self?.amount = String(state.amount)
                    self?.note = state.note
                    self?.selectedTransactionOption = state.type.name
                    self?.selectedCategoryOption = state.category
                    self?.selectedDate = Date(timeIntervalSince1970: Double(state.dateCreated) / 1000)
                }
            }
        })
    }
    

    
    func initializeData(transactionId: Int64) {
        self.transactionId = transactionId
        viewModel.initializeData(id: transactionId)
    }
    
    
    func save(){
        let transaction = getTransaction()
        viewModel.onClickButton(transaction: transaction, transactionId: transactionId)
    }
    
    
    private func getTransaction() -> TransactionParam {
        return TransactionParam(
            title: title,
            amount: Double(amount) ?? 0.0,
            type: TransactionTypeKt.asLong(selectedTransactionOption),
            category: selectedCategoryOption,
            dateCreated: Int64(selectedDate.timeIntervalSince1970 * 1000),
            note: note
        )
    }
}
