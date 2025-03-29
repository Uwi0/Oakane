import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class SettingsViewModel: ObservableObject {
    
    @Published var uiState: SettingsState = SettingsState.companion.default()
    @Published var uiEffect: SettingsEffect? = nil
    
    private let viewModel: SettingsViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    init() {
        observeState()
        observeEffect()
    }
    
    func initData() {
        viewModel.doInitData(showDrawer: true)
    }
    
    func handle(event: SettingsEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("Completion: \(completion)")
        } receiveValue: { [weak self] state in
            guard let self = self else { return }
            self.update(state: state)
        }
    }
    
    private func update(state: SettingsState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("Completion: \(completion)")
        } receiveValue: { [weak self] effect in
            guard let self = self else { return }
            self.update(effect: effect)
        }
    }
    
    private func update(effect: SettingsEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
}
