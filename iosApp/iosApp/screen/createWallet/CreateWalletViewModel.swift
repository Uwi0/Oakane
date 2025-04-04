import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class CreateWalletViewModel: ObservableObject {
    
    @Published private(set) var uiState: CreateWalletState = CreateWalletState.companion.default()
    @Published var uiEffect: CreateWalletEffect? = nil
    
    private var viewModel: CreateWalletViewModelKt = Koin.shared.get()
    
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    func initData(walletId: Int64) {
        viewModel.doInitData(walletId: walletId)
        observeState()
        observeEffect()
    }
    
    func handle(event: Shared.CreateWalletEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeState() {
        let publihser = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publihser.sink { completion in
            print("Completion: \(completion)")
        } receiveValue: { [weak self] state in
            guard let self else { return }
            self.update(state: state)
        }
    }
    
    private func update(state: CreateWalletState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { [weak self] effect in
            guard let self else { return }
            self.update(effect: effect)
        }
    }
    
    private func update(effect: CreateWalletEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
}
