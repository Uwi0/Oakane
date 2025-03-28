import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class CategoriesViewModel: ObservableObject {
    @Published var uiState = CategoriesState.companion.default()
    @Published var uiEffect: CategoriesEffect? = nil
    
    private var viewModel: CategoriesViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    init() {
        viewModel.initializeData(showDrawer: true)
        observeUiState()
        observerUiEffect()
    }
    
    func handle(event: CategoriesEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: CategoriesState){
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observerUiEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    func update(effect: CategoriesEffect){
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
    
}
