import SwiftUI
import Shared

struct CategoriesContentView: View {
    
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    
    var body: some View {
        VStack {
            NavigationTopAppbar(title: "Categories", onAction: { onEvent(.NavigateBack()) })
            OutlinedSearchTextFieldView(
                query: Binding(
                    get: { uiState.searchQuery },
                    set: { onEvent(.Search(query: $0))
                    }
                ),
                placeHolder: "Search Categories..."
            )
            .padding(.horizontal, 16)
            TabBarView(
                currentTab: Binding(
                    get: { uiState.selectedTab },
                    set: { index in onEvent(.ChangeTab(index: Int32(index))) }
                ),
                tabBarOptions: TransactionType.allCases.map{ type in type.name }
            )
            TabView(
                selection: Binding(
                    get: { uiState.selectedTab },
                    set: { index in onEvent(.ChangeTab(index: Int32(index))) }
                )
            ) {
                CategoriesView(
                    categories: uiState.categories.filter{ category in category.type == .income },
                    onClick: { category in onEvent(.OnTapped(category: category)) }
                )
                .tag(0)
                
                CategoriesView(
                    categories: uiState.categories.filter{ category in category.type == .expense },
                    onClick: { category in onEvent(.OnTapped(category: category)) }
                )
                .tag(1)
                
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            
        }
    }
}
