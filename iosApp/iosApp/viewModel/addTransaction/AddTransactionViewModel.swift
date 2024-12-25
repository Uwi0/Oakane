import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class AddTransactionViewModel: ObservableObject {
    
    @Published var uiState: AddTransactionState = AddTransactionState()
    private let viewModel: AddTransactionViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    
    init() {
        observeUiState()
    }
    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(id: transactionId)
    }
    
    
    func handle(event: AddTransactionEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink{ completion in
            print("complete: \(completion)")
        } receiveValue: { state in
            DispatchQueue.main.async {
                self.uiState = AddTransactionState(state: state)
            }
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
    }
}
