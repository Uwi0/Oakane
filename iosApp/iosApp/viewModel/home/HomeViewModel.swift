import Foundation
import Shared

final class HomeViewModel: ObservableObject {
    
    @Published var uiState: HomeState = HomeState()
    @Published var uiEffects: HomeEffect? = nil
    
    private var viewModel: HomeViewModelAdapter = Koin.shared.get()
    
    init(){
        self.viewModel.observeState { [weak self] state in
            DispatchQueue.main.async {
                self?.uiState.transactions = state.transactions
                self?.uiState.goals = state.goals
            }
        }
        self.viewModel.observeEffect { [weak self] effects in
            DispatchQueue.main.async {
                self?.uiEffects = effects
            }
        }
    }
    
    func initViewModel(){
        viewModel.doInitViewModel()
    }
    
    func handle(event: HomeEvent) {
        viewModel.handle(event: event)
    }
    
}
