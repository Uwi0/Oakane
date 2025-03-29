import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class ReminderViewModel: ObservableObject {
    
    @Published private(set) var uiState: ReminderState = ReminderState.companion.default()
    @Published var uiEffect: ReminderEffect? = nil
    
    private var viewModel: ReminderViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    func initViewModel() {
        viewModel.doInitData()
        observeUiState()
        observeUiEffect()
    }
    
    func handle(event: ReminderEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            guard let self = self else { return }
            self.update(state: state)
        }
    }
    
    private func update(state: ReminderState) {
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeUiEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            guard let self = self else { return }
            self.update(effect: effect)
        }
    }
    
    private func update(effect: ReminderEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
}
