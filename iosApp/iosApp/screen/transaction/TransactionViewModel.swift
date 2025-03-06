import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TransactionViewModel: ObservableObject {
    
    @Published var uiState: TransactionState = TransactionState.companion.default()
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
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: TransactionState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func obsereUIEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: TransactionEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
}
