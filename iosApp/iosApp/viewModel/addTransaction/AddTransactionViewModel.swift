import Foundation
import Shared

final class AddTransactionViewModel: ObservableObject {
    
    @Published var uiState: AddTransactionState = AddTransactionState()
    private let viewModel: AddTransactionViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeState(onStateChange: { [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.title = state.title
                self?.uiState.amount = state.amount
                self?.uiState.transactionType = state.transactionType
                self?.uiState.category = state.category
                self?.uiState.selectedDate = state.date
                self?.uiState.note = state.note
                self?.uiState.showSheet = state.sheetShown
                self?.uiState.categories = state.categories
            }
        })
    }
    

    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(id: transactionId)
    }
    
    
    func handle(event: AddTransactionEvent) {
        viewModel.handleEvent(event: event)
    }
}
