import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class HomeViewModel: ObservableObject {
    
    @Published var uiState: HomeState = HomeState()
    @Published var uiEffects: HomeEffect? = nil
    
    private var viewModel: HomeViewModelKt = Koin.shared.get()
    private var uiStateCancellable: AnyCancellable?
    private var uiEffectCancellable: AnyCancellable?
    
    init(){
        observeUiState()
        observeUiEffect()
    }
    
    func initViewModel(){
        viewModel.initializeData()
    }
    
    func handle(event: HomeEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState(){
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        uiStateCancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { state in
            DispatchQueue.main.async {
                self.uiState = HomeState(state: state)
            }
        }
    }
    
    private func observeUiEffect(){
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { effect in
            DispatchQueue.main.async {
                self.uiEffects = effect
            }
        }
    }
    
    deinit {
        uiStateCancellable?.cancel()
        uiEffectCancellable?.cancel()
    }
    
}
