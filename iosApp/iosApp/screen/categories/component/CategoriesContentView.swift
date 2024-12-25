import SwiftUI
import Shared

struct CategoriesContentView: View {
    @Binding var selectedTab: Int
    @Binding var searchQuery: String
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    
    var body: some View {
        VStack {
            NavigationTopAppbar(title: "Categories", navigateBack: { onEvent(.NavigateBack()) })
            OutlinedSearchTextFieldView(query: $searchQuery, placeHolder: "Search Categories...")
                .padding(.horizontal, 16)
            TabBarView(currentTab: $selectedTab, tabBarOptions: uiState.tabBars)
            TabView(selection: $selectedTab) {
                CategoriesView(
                    categories: uiState.incomeCategories,
                    onClick: { category in onEvent(.OnTapped(category: category)) }
                )
                .tag(0)
                
                CategoriesView(
                    categories: uiState.expenseCategories,
                    onClick: { category in onEvent(.OnTapped(category: category)) }
                )
                .tag(1)

            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            
        }
    }
}
