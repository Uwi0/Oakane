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
    
    private var viewModel: CategoriesViewModelAdapter = Koin.shared.get()
    
    init() {
        viewModel.observeData { [weak self] uiState in
            DispatchQueue.main.async {
                self?.uiState.categories = uiState.filteredCategories
                self?.uiState.sheetContent = uiState.sheetContent
                self?.uiState.categoryName = uiState.categoryName
                self?.uiState.selectedColor = uiState.defaultColor
                self?.uiState.defaultColors = uiState.categoriesColor
                self?.uiState.selectedIcon = uiState.defaultIcon
                self?.uiState.showSheet = uiState.showSheet
                self?.uiState.imageName = uiState.fileName
                self?.uiState.categoryId = uiState.categoryId
                self?.uiState.isEditMode = uiState.isEditMode
            }
        }
    }
    
    func handle(event: CategoriesEvent) {
        viewModel.handleEvent(event: event)
    }
    
}
