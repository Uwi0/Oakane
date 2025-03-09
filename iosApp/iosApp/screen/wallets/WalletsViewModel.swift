import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class WalletsViewModel: ObservableObject {
    @Published var uiState: WalletsState = WalletsState.companion.default()
    @Published var uiEffect: WalletsEffect? = nil
    private var viewModel: WalletsViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    init() {
        viewModel.initializeData(showDrawer: true)
        observeUiState()
        observeUiEffect()
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: WalletsState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeUiEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: WalletsEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    func handle(event: WalletsEvent){
        viewModel.handleEvent(event: event)
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
}
