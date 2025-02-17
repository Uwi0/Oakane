import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class WalletsViewModel: ObservableObject {
    @Published var uiState: WalletsState = WalletsState()
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
        } receiveValue: { value in
            DispatchQueue.main.async {
                self.uiState = WalletsState(walletsState: value)
            }
        }
    }
    
    private func observeUiEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        uiEffectCancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { value in
            DispatchQueue.main.async {
                self.uiEffect = value
            }
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
