import Foundation
import Shared

final class CategoriesViewModel: ObservableObject {
    @Published var categories: [CategoryModel] = []
    
    private var viewModel: CategoriesViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] categories in
            DispatchQueue.main.async {
                self?.categories = categories
            }
        }
    }
}
