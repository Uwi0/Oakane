import Foundation
import Shared

final class HomeViewModel: ObservableObject {
    
    @Published var transactions: [TransactionModel] = []
    
    private var viewModel: HomeViewModelAdapter
    
    init(){
        self.viewModel = HomeViewModelAdapter()
        viewModel.doInitViewModel()
        self.viewModel.observeState { [weak self] state in
            DispatchQueue.main.async {
                self?.transactions = state
                print(state.count)
            }
        }
    }
    
    func initViewModel(){
        viewModel.doInitViewModel()
    }
    
    deinit {
        viewModel.clearAdapter()
    }
    
}
