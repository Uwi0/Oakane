import Foundation
import Shared

final class HomeViewModelWrapper: ObservableObject {
    
    @Published var transactions: [TransactionModel] = []
    
    private var viewModel: IOSHomeViewModel
    
    init(){
        self.viewModel = IOSHomeViewModel()
        viewModel.doInitViewModel()
        self.viewModel.observeState { [weak self] state in
            DispatchQueue.main.async {
                self?.transactions = state
                print(state.count)
            }
        }
    }
    
}
