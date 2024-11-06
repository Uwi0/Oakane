import Foundation
import Shared

final class CategoriesViewModel: ObservableObject {
    @Published var uiState = CategoriesState()
    let tabBars = TransactionType.allCases.map(\.name)
    
    var expenseCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(uiState.categories, type: .expense)
    }
    
    var incomeCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(uiState.categories, type: .income)
    }
    
    private var viewModel: CategoriesViewModelAdapter = Koin.instance.get()
    
    init() {
        viewModel.observeData { [weak self] uiState in
            DispatchQueue.main.async {
                self?.uiState.categories = uiState.filteredCategories
                self?.uiState.sheetContent = uiState.sheetContent
                self?.uiState.categoryName = uiState.categoryName
                self?.uiState.selectedColor = uiState.defaultSelectedColor
                self?.uiState.defaultColors = uiState.categoriesColor
                self?.uiState.selectedIcon = uiState.defaultIcon
                self?.uiState.showSheet = uiState.showSheet
            }
        }
    }
    
    func onSearchQueryChanged(query: String) {
        viewModel.handleEvent(event: CategoriesEvent.Search(query: query))
    }
    
    func onEvent(event: CategoriesEvent) {
        viewModel.handleEvent(event: event)
    }
    
}
