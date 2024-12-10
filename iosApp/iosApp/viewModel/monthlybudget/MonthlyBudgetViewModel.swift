import Foundation
import Shared

final class MonthlyBudgetViewModel: ObservableObject {
    @Published var uiState: MonthlyBudgetState = MonthlyBudgetState()
    @Published var uiEffect: MonthlyBudgetEffect? = nil
    
    private let viewModel: MonthlyBudgetViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeState{ [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.isEditMode = state.isEditMode
                self?.uiState.amount = Int(state.realAmount)
            }
        }
        viewModel.observeEffect{ [weak self] effect in
            DispatchQueue.main.async {
                self?.uiEffect = effect
            }
        }
    }
    
    func initData(){
        viewModel.doInitData()
    }
    
    func handle(event: MonthlyBudgetEvent){
        viewModel.handleEvent(event: event)
    }
}
