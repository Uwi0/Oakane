import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class WalletsViewModel: ObservableObject {
    @Published var uiState: WalletsState = WalletsState()
    private var viewModel: WalletsViewModelKt = Koin.shared.get()
    private var cancellable: AnyCancellable?
    
    init() {
        viewModel.initializeData()
        observeUiState()
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        cancellable = publisher.sink { completion in
            print("completion: \(completion)")
        } receiveValue: { value in
            DispatchQueue.main.async {
                self.uiState = WalletsState(walletsState: value)
            }
        }
    }
    
    func handle(event: WalletsEvent){
        viewModel.handleEvent(event: event)
    }
    
    deinit {
        cancellable?.cancel()
    }
}
