import SwiftUI
import Shared

struct CategoriesContentView: View {
    @Binding var selectedTab: Int
    @Binding var searchQuery: String
    let tabBars: [String]
    let expenseCategories: [CategoryModel]
    let incomeCategories: [CategoryModel]
    
    
    var body: some View {
        VStack {
            Divider()
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Categories...")
                .padding(.horizontal, 16)
            TabBarView(currentTab: $selectedTab, tabBarOptions: tabBars)
            TabView(selection: $selectedTab) {
                CategoriesView(categories: expenseCategories).tag(0)
                CategoriesView(categories: incomeCategories).tag(1)
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            
        }
    }
}