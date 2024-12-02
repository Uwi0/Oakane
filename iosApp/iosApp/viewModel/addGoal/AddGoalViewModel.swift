import Foundation
import Shared

final class AddGoalViewModel: ObservableObject {
    
    @Published var uiState: AddGoalState = AddGoalState()
    @Published var uiEffect: AddGoalEffect? = nil
    
    private let viewModel: AddGoalViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeState { [weak self] uiState in
            DispatchQueue.main.async {
                self?.uiState.fileName = uiState.fileName
                self?.uiState.goalName = uiState.goalName
                self?.uiState.targetAmount = Int(uiState.amount)
                self?.uiState.note = uiState.note
                self?.uiState.startDate = uiState.startDate
                self?.uiState.endDate = uiState.endDate
            }
        }
        viewModel.observeEffect { [weak self] uiEffect in
            DispatchQueue.main.async {
                self?.uiEffect = uiEffect
            }
        }
    }
    
    func initData(goalId: Int64){
        viewModel.doInitData(goalId: goalId)
    }
    
    func handle(event: AddGoalEvent) {
        viewModel.handleEvent(event: event)
    }
}
