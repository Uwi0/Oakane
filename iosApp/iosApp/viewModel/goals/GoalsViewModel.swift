import Foundation
import Shared

final class GoalsViewModel: ObservableObject {
    
    @Published var uiState: GoalsState = GoalsState()
    private let viewModel: GoalsViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeState{ [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.goals = state.filteredGoals
            }
        }
    }
    
    func handle(event: GoalsEvent){
        viewModel.handle(event: event)
    }
}
