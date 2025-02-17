import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TransactionsViewModel: ObservableObject {
    
    @Published var uiState: TransactionsState = TransactionsState()
    @Published var uiEffect: TransactionsEffect? = nil
    
    private var viewModel: TransactionsViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    init () {
        observeUIState()
        observeUIEffect()
    }
    
    func initData() {
        viewModel.initializeData(showDrawer: true)
    }
    
    func handle(event: TransactionsEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUIState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { state in
            DispatchQueue.main.async {
                self.uiState = TransactionsState(state: state)
            }
        }
    }
    
    private func observeUIEffect() {
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
        uiEffectCancellable?.cancel()
        uiStateCancellable?.cancel()
    }
}
