import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class ReportsViewModel: ObservableObject {
    
    @Published var uiState: ReportsState = ReportsState()
    @Published var uiEffect: ReportsEffect? = nil
    
    private var viewModel: ReportsViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    init() {
        viewModel.initializeData()
        observeState()
        observeEffect()
    }
    
    func handle(event: ReportsEvent){
        viewModel.handleEVent(event: event)
    }
    
    private func observeState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: ReportsStateKt) {
        DispatchQueue.main.async {
            self.uiState = ReportsState(state: state)
        }
    }
    
    private func observeEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: ReportsEffect){
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
    
}
