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
    var selectedColor: Int32 = 0
    var defaultColors: [String] = []
}
