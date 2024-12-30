import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class SettingsViewModel: ObservableObject {
    
    @Published var uiEffect: SettingsEffect?
    
    private let viewModel: SettingsViewModelKt = Koin.shared.get()
    private var effectCancellable: AnyCancellable?
    
    init() {
        observeEffect()
    }
    
    func handle(event: SettingsEvent) {
        viewModel.handleEvent(event: event)
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
