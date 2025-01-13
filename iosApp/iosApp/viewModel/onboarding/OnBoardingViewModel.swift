import Foundation
import Shared
import KMPNativeCoroutinesCombine
import Combine

final class OnBoardingViewModel: ObservableObject {
    
    @Published private(set) var uiState: OnBoardingState = OnBoardingState()
    @Published var uiEffect: OnBoardingEffect? = nil
    
    private let viewModel: OnBoardingViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    func initData() {
        observeUiState()
        observeUiEffect()
    }
    
    func handle(event: OnBoardingEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: OnBoardingStateKt) {
        DispatchQueue.main.async {
            self.uiState = OnBoardingState(state: state)
        }
    }
    
    private func observeUiEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: OnBoardingEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
    
}
