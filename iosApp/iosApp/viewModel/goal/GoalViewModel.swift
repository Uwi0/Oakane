import Foundation
import Shared

final class GoalViewModel: ObservableObject {
    
    @Published var uiState: GoalState = GoalState()
    @Published var uiEffect: GoalEffect? = nil
    
    private let viewModel: GoalViewModelAdapter = Koin.shared.get()
    
    init() {
        viewModel.observeUiState { [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.fileName = state.goal.fileName
                self?.uiState.title = state.goal.goalName
                self?.uiState.savedAmount = state.savedAmount
                self?.uiState.targetAmount = state.targetAmount
                self?.uiState.progress = state.goal.progress
                self?.uiState.startDate = state.goal.startDate
                self?.uiState.endDate = state.goal.endDate
                self?.uiState.daysLeft = state.dayLeft
                self?.uiState.note = state.goal.note
                self?.uiState.isDialogShown = state.dialogShown
                self?.uiState.dialogContent = state.dialogContent
            }
        }
        viewModel.observeUiEffect { [weak self] effect in
            DispatchQueue.main.async {
                self?.uiEffect = effect
            }
        }
    }
    
    func initializeData(goalId: Int64) {
        viewModel.doInitViewModel(goalId: goalId)
    }
    
    
    
    func handle(event: GoalEvent) {
        viewModel.handleEvent(event: event)
    }
}
