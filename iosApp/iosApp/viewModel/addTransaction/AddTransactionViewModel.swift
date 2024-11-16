import Foundation
import Shared

final class AddTransactionViewModel: ObservableObject {
    
    @Published var uiState: AddTransactionState = AddTransactionState()
    
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
                    self?.uiState.title = state.title
                    self?.uiState.amount = state.amount
                    self?.uiState.transactionType = state.transactionType
                    self?.uiState.category = state.category
                    self?.uiState.selectedDate = state.date
                    self?.uiState.note = state.note
                }
            }
        })
    }
    

    
    func initializeData(transactionId: Int64) {
        self.transactionId = transactionId
        viewModel.initializeData(id: transactionId)
    }
    
    
    func handle(event: AddTransactionEvent) {
        viewModel.handleEvent(event: event)
    }
}
