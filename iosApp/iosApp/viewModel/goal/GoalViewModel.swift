import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class GoalViewModel: ObservableObject {
    
    @Published var uiState: GoalState = GoalState()
    @Published var uiEffect: GoalEffect? = nil
    
    private let viewModel: GoalViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    init() {
        observeState()
        observeEffect()
    }
    
    func initializeData(goalId: Int64) {
        viewModel.initializeData(goalId: goalId)
    }
    
    func handle(event: GoalEvent) {
        viewModel.handleEvent(event: event)
    }
    
    private func observeState() {
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: GoalStateKt){
        DispatchQueue.main.async {
            self.uiState = GoalState(state: state)
        }
    }
    
    private func observeEffect() {
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("Completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: GoalEffect){
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
}
