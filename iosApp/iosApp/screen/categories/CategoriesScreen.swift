import SwiftUI

struct CategoriesScreen: View {
    @StateObject private var viewModel = CategoriesViewModel()
    var body: some View {
        VStack {
            TabBarView(currentTab: $viewModel.selectedTab, tabBarOptions: viewModel.tabBars)
            TabView(selection: $viewModel.selectedTab) {
                CategoriesView(categories: viewModel.expenseCategories).tag(0)
                CategoriesView(categories: viewModel.incomeCategories).tag(1)
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
        }
        .edgesIgnoringSafeArea(.all)
    }
}

#Preview {
    CategoriesScreen()
}
