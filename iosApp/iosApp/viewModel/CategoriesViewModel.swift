import Foundation
import Shared

final class CategoriesViewModel: ObservableObject {
    @Published var categories: [CategoryModel] = []
    @Published var selectedTab: Int = 0
    let tabBars = TransactionType.entries.map(\.name)
    
    var expenseCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(categories, type: .expense)
    }
    
    var incomeCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(categories, type: .income)
    }
    
    private var viewModel: CategoriesViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] categories in
            DispatchQueue.main.async {
                self?.categories = categories
            }
        }
    }
}
