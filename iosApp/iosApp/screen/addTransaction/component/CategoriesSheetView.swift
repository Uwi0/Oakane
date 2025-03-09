import SwiftUI
import Shared

struct CategoriesSheetView: View {
    
    let categories: [CategoryModel]
    let onEvent: (AddTransactionEvent) -> Void
    
    @State private var searchQuery: String = ""
    @State private var selectedTab: Int32 = 0
    private let tabBars = TransactionType.allCases.map { type in type.name }
    

    private var filteredCategories: [CategoryModel] {
        if searchQuery.isEmpty {
            return categories
        } else {
            return categories.filter { category in
                category.name.lowercased().contains(searchQuery.lowercased())
            }
        }
    }
    
    private var transactionType: TransactionType {
        Int32(selectedTab).asTransactionType()
    }
    
    private var incomeCategories: [CategoryModel] {
        filteredCategories.filter { category in category.type == .income }
    }
    
    private var expenseCategories: [CategoryModel] {
        filteredCategories.filter { category in category.type == .expense }
    }

    var body: some View {
        VStack(spacing: 16) {
            VStack {
                OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Category...")
                TabBarView(
                    currentTab: $selectedTab,
                    tabBarOptions: tabBars)
            }
            .padding(.horizontal, 16)
            .padding(.top, 24)
            TabView(selection: $selectedTab) {
                CategoriesView(
                    categories: incomeCategories,
                    onClick: { category in onEvent(.SetCategory(value: category)) }
                )
                .tag(0)
                
                CategoriesView(
                    categories: expenseCategories,
                    onClick: { category in onEvent(.SetCategory(value: category)) }
                )
                .tag(1)
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            .onChange(of: selectedTab) {
                onEvent(.ChangeType(value: transactionType))
            }
        }
    }
}
#Preview {
    CategoriesSheetView(categories: [], onEvent: { _ in })
}
