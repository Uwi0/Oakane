import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class AddTransactionViewModel: ObservableObject {
    
    @Published var uiState: AddTransactionState = AddTransactionState.companion.default()
    private let viewModel: AddTransactionViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(id: transactionId)
        observeUiState()
    }
    
    
    func handle(event: AddTransactionEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink{ completion in
            print("complete: \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: AddTransactionState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
    }
}
