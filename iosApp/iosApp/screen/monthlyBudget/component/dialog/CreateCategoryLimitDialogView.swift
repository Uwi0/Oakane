import SwiftUI
import Shared

enum CreateCategoryContent {
    case mainMenu, selectCategoryLimit
}

struct CreateCategoryLimitDialogView: View {
    
    let categories: [CategoryModel]
    
    @State var content: CreateCategoryContent = .mainMenu
    @State var limitAmount: Int = 0
    @State var category: CategoryModel
    
    init(categories: [CategoryModel]){
        self.categories = categories
        self.category = categories.first ?? defaultCategoryModel
    }
    
    var body: some View {
        switch content {
        case .mainMenu: MainContentView(
            category: category,
            limitAmount: $limitAmount,
            content: $content)
        case .selectCategoryLimit: SelectCategoryLimitView(
            categories: categories,
            onClick: { category in
                content = .mainMenu
                self.category = category
            })
        }
    }
}

fileprivate struct MainContentView: View {
    
    let category: CategoryModel
    @Binding var limitAmount: Int
    @Binding var content: CreateCategoryContent
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Add your category limit").font(Typography.titleLarge)
            Spacer().frame(height: 24)
            OutlinedCurrencyTextFieldView(value: $limitAmount, onValueChange: { _ in })
            Spacer().frame(height: 16)
            ButtonSelectCategoryLimitView(category: category, onClick: { content = .selectCategoryLimit})
            Spacer().frame(height: 32)
            MainDialogButton()
        }
        .padding(24)
    }
}

fileprivate struct MainDialogButton: View {
    
    var body: some View {
        HStack(spacing: 16) {
            TextButtonView(
                title: "Cancel",
                onClick: { }
            ).frame(width: 120,height: 48)
            FilledButtonView(
                text: "Save",
                onClick: { }
            ).frame(width: 120,height: 48)
        }
        .frame(maxWidth: .infinity, alignment: .trailing)
    }
}

fileprivate struct SelectCategoryLimitView: View {
    
    let categories: [CategoryModel]
    let onClick: (CategoryModel) -> Void
    @State private var searchQuery: String = ""
    
    private var filteredCategories: [CategoryModel] {
        if searchQuery.isEmpty {
            return categories
        } else {
            return categories.filter{ category in
                category.name.lowercased().contains(searchQuery.lowercased())
            }
        }
        
    }
    
    var body: some View {
        VStack(spacing: 24) {
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search...")
            ScrollView{
                ForEach(filteredCategories, id: \.id) { category in
                    CategoryItemView(category: category).onTapGesture {onClick(category)}
                }
            }
            .scrollIndicators(.hidden)
        }
        .frame(maxHeight: 360)
    }
}

#Preview {
    CreateCategoryLimitDialogView(categories: [])
}
