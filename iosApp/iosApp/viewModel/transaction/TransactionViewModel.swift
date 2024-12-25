import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TransactionViewModel: ObservableObject {
    
    @Published var uiState: TransactionState = TransactionState()
    @Published var uiEffect: TransactionEffect? = nil
    
    private var viewModel: TransactionViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    init() {
        observeUIState()
        obsereUIEffect()
    }
    
    func initializeData(transactionId: Int64) {
        viewModel.initializeData(id: transactionId)
    }
    
    func handle(event: TransactionEvent){
        viewModel.handleEvent(event: event)
    }
    
    private func observeUIState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { state in
            DispatchQueue.main.async {
                self.uiState = TransactionState(state: state)
            }
        }
    }
    
    private func obsereUIEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { effect in
            DispatchQueue.main.async {
                self.uiEffect = effect
            }
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
}
