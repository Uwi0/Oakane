import Foundation
import Shared

final class HomeViewModel: ObservableObject {
    
    @Published var transactions: [TransactionModel] = []
    
    private var viewModel: HomeViewModelAdapter = Koin.instance.get()
    
    init(){
        self.viewModel.observeState { [weak self] state in
            DispatchQueue.main.async {
                self?.transactions = state
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
