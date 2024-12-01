import Foundation
import Shared

final class GoalsViewModel: ObservableObject {
    
    @Published var uiState: GoalsState = GoalsState()
    @Published var uiEffect: GoalsEffect? = nil
    private let viewModel: GoalsViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeState{ [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.goals = state.filteredGoals
            }
        }
        viewModel.observeEffect{ [weak self] effect in
            DispatchQueue.main.async {
                self?.uiEffect = effect
            }
        }
    }
    
    func handle(event: GoalsEvent){
        viewModel.handle(event: event)
    }
}
