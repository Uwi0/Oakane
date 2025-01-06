import Foundation
import Shared

struct CategoriesState {
    var categories: [CategoryModel] = []
    var selectedTab: Int = 0
    var searchQuery: String = ""
    var showSheet: Bool = false
    var sheetContent: CategoriesSheetContent = .create
    var categoryName: String = ""
    var selectedIcon: CategoryIconName = .default
    var imageName: String = ""
    var selectedColor: Int64 = 0
    var defaultColors: [String] = []
    var categoryId: Int64 = 0
    var isEditMode: Bool = false
    
    init(){}
    
    init(state: CategoriesStateKt){
        categories = state.filteredCategories
        sheetContent = state.sheetContent
        categoryName = state.categoryName
        selectedColor = state.defaultColor
        defaultColors = state.categoriesColor
        selectedIcon = state.defaultIcon
        showSheet = state.showSheet
        imageName = state.fileName
        categoryId = state.categoryId
        isEditMode = state.isEditMode
    }
    
    let tabBars = TransactionType.allCases.map(\.name)
    
    var expenseCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(categories, type: .expense)
    }
    
    var incomeCategories: [CategoryModel] {
        CategoryModelKt.swiftFilterBy(categories, type: .income)
    }
}
