import SwiftUI
import Shared

enum CreateCategoryContent {
    case mainMenu, selectCategoryLimit
}

struct CreateCategoryLimitDialogView: View {
    
    private let categories: [CategoryModel]
    private let onEvent: (MonthlyBudgetEvent) -> Void
    
    @State private var content: CreateCategoryContent = .mainMenu
    @State private var limitAmount: Int = 0
    @State private var category: CategoryModel
    
    init(
        categoryLimit: CategoryLimitModel?,
        categories: [CategoryModel],
        onEvent: @escaping (MonthlyBudgetEvent) -> Void
    ){
        self.categories = categories
        self.onEvent = onEvent
        if categoryLimit == nil {
            self.category = categories.first ?? defaultCategoryModel
        } else {
            self.category = categoryLimit!.category
            self.limitAmount = Int(categoryLimit!.limit)
        }
    }
    
    var body: some View {
        switch content {
        case .mainMenu: MainContentView(
            category: category,
            limitAmount: $limitAmount,
            content: $content,
            onEvent: onEvent)
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
    let onEvent: (MonthlyBudgetEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Add your category limit").font(Typography.titleLarge)
            Spacer().frame(height: 24)
            OutlinedCurrencyTextFieldView(value: $limitAmount, onValueChange: { _ in })
            Spacer().frame(height: 16)
            ButtonSelectCategoryLimitView(category: category, onClick: { content = .selectCategoryLimit})
            Spacer().frame(height: 32)
            MainDialogButton(
                onPositiveButton: {
                    onEvent(.CreateCategoryLimitBy(categoryId: category.id, limitAmount: Double(limitAmount)))
                },
                onNegativeButton: { onEvent(.Dialog(shown: false))}
            )
        }
        .padding(24)
    }
}

fileprivate struct MainDialogButton: View {
    
    let onPositiveButton: () -> Void
    let onNegativeButton: () -> Void
    
    var body: some View {
        HStack(spacing: 16) {
            TextButtonView(
                title: "Cancel",
                onClick: onNegativeButton
            ).frame(width: 120,height: 48)
            FilledButtonView(
                text: "Save",
                onClick: onPositiveButton
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
    CreateCategoryLimitDialogView(categoryLimit: nil, categories: [], onEvent: { _ in })
}
