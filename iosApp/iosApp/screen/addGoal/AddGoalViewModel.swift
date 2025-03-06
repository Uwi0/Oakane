import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class AddGoalViewModel: ObservableObject {
    
    @Published var uiState: AddGoalState = AddGoalState.companion.default()
    @Published var uiEffect: AddGoalEffect? = nil
    
    private let viewModel: AddGoalViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    func initData(goalId: Int64){
        viewModel.doInitData(goalId: goalId)
        observeUiState()
        observeUiEffect()
    }
    
    func handle(event: AddGoalEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeUiState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink{ completion in
            print("completion: \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: AddGoalState){
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeUiEffect(){
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink{ completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: AddGoalEffect){
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
}
