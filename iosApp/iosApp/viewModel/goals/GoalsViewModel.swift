import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class GoalsViewModel: ObservableObject {
    
    @Published var uiState: GoalsState = GoalsState()
    @Published var uiEffect: GoalsEffect? = nil
    private let viewModel: GoalsViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    init() {
        observeState()
        observeEffect()
    }
    
    func initData(){
        viewModel.doInitData()
    }
    
    func handle(event: GoalsEvent){
        viewModel.handleEvent(event: event)
    }
    
    private func observeState(){
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("Completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: GoalsStateKt){
        DispatchQueue.main.async {
            self.uiState.goals = state.filteredGoals
        }
    }
    
    private func observeEffect(){
        let publisher = createPublisher(for: viewModel.uiEffect)
        effectCancellable = publisher.sink { completion in
            print("Completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: GoalsEffect){
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
}
