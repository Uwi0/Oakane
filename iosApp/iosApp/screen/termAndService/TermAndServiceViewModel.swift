import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TermAndServiceViewModel: ObservableObject {
    @Published var uiState = TermAndServiceState.companion.default()
    @Published var uiEffect: TermAndServiceEffect? = nil
    private var viewModel: TermAndServiceViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    func initData() {
        observeUiState()
        observeUiEffect()
    }
    
    func handle(event: TermAndServiceEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publihser = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publihser.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: TermAndServiceState) {
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
    
    private func update(effect: TermAndServiceEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
}
