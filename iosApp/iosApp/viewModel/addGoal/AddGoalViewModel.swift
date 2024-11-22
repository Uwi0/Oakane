import Foundation
import Shared

final class AddGoalViewModel: ObservableObject {
    
    @Published var uiState: AddGoalState = AddGoalState()
    @Published var uiEffect: AddGoalEffect? = nil
    
    private let viewModel: AddGoalViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeEffect { [weak self] uiEffect in
            DispatchQueue.main.async {
                self?.uiEffect = uiEffect
            }
        }
    }
    
    func handle(event: AddGoalEvent) {
        viewModel.handleEvent(event: event)
    }
}
