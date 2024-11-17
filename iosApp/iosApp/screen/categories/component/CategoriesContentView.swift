import SwiftUI
import Shared

struct CategoriesContentView: View {
    @Binding var selectedTab: Int
    @Binding var searchQuery: String
    let tabBars: [String]
    let expenseCategories: [CategoryModel]
    let incomeCategories: [CategoryModel]
    let onEvent: (CategoriesEvent) -> Void
    
    
    var body: some View {
        VStack {
            Divider()
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Categories...")
                .padding(.horizontal, 16)
            TabBarView(currentTab: $selectedTab, tabBarOptions: tabBars)
            TabView(selection: $selectedTab) {
                CategoriesView(
                    categories: incomeCategories,
                    onClick: { category in onEvent(.OnTapped(category: category)) }
                )
                .tag(0)
                
                CategoriesView(
                    categories: expenseCategories,
                    onClick: { category in onEvent(.OnTapped(category: category)) }
                )
                .tag(1)

            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            
        }
    }
}
