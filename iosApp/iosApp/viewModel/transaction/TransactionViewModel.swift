import Foundation
import Shared

final class TransactionViewModel: ObservableObject {
    
    @Published var uiState: TransactionState = TransactionState()
    @Published var uiEffect: TransactionEffect? = nil
    
    private var viewModel: TransactionViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.transaction = state.transaction
            }
        }
        viewModel.observeEffect { [weak self] effect in
            DispatchQueue.main.async {
                self?.uiEffect = effect
            }
        }
    }
    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(transactionId: transactionId)
    }
    
    func handle(event: TransactionEvent){
        viewModel.handle(event: event)
    }
}
