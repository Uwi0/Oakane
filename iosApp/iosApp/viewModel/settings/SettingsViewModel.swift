import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class SettingsViewModel: ObservableObject {
    
    @Published var uiState: SettingsState = SettingsState()
    @Published var uiEffect: SettingsEffect?
    
    private let viewModel: SettingsViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    init() {
        observeState()
        observeEffect()
    }
    
    func handle(event: SettingsEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("Completion: \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: SettingsStateKt) {
        DispatchQueue.main.async {
            self.uiState = SettingsState(state: state)
        }
    }
    
    private func observeEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("Completion: \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: SettingsEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        effectCancellable?.cancel()
    }
}
