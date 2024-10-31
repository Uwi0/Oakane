import Foundation
import Shared

final class CategoriesViewModel: ObservableObject {
    @Published var categories: [CategoryModel] = []
    @Published var selectedTab: Int = 0
    @Published var searchQuery: String = ""
    let tabBars = TransactionType.allCases.map(\.name)
    
    var expenseCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(categories, type: .expense)
    }
    
    var incomeCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(categories, type: .income)
    }
    
    private var viewModel: CategoriesViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] uiState in
            DispatchQueue.main.async {
                self?.categories = uiState.filteredCategories
            }
        }
    }
    
    func onSearchQueryChanged(query: String) {
        viewModel.handleEvent(event: CategoriesEvent.Search(query: query))
    }
    
}
