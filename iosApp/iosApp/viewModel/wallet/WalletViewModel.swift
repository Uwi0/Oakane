import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class WalletViewModel: ObservableObject {
    @Published var uiState: WalletState = WalletState.companion.default()
    @Published var uiEffect: WalletEffect? = nil
    private var viewModel: WalletViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    func iniData(walletId: Int64) {
        viewModel.doInitData(walletId: walletId)
        observerUiState()
        observeUiEffect()
    }
    
    func handle(event: WalletEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observerUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: WalletState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeUiEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: WalletEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
}
