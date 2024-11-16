import Foundation
import Shared


final class TransactionsViewModel: ObservableObject {
    
    @Published var uiState: TransactionsState = TransactionsState()
    @Published var uiEffect: TransactionsEffect? = nil
    
    private var viewModel: TransactionsViewModelAdapter = Koin.instance.get()
    
    init () {
        viewModel.observeState { [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.transactions = state.transactions
                self?.uiState.searchQuery = state.searchQuery
                self?.uiState.selectedType = state.selectedType
                self?.uiState.selectedDate = state.selectedDate
                self?.uiState.selectedCategory = state.selectedCategory
                self?.uiState.sheetSown = state.sheetShown
                self?.uiState.sheetContent = state.sheetContent
            }
        }
        
        viewModel.observeEffect { [weak self] effect in
            DispatchQueue.main.async {
                self?.uiEffect = effect
            }
        }
    }
    
    func handle(event: TransactionsEvent) {
        viewModel.handleEvent(event: event)
    }
    
}
