import SwiftUI

struct CategoriesScreen: View {
    @StateObject private var viewModel = CategoriesViewModel()
    let toolbarContent = ToolBarContent(title: "Categories")
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                ColorTheme.surface
                    .ignoresSafeArea()
                CategoriesContentView(
                    selectedTab: $viewModel.selectedTab,
                    searchQuery: $viewModel.searchQuery,
                    tabBars: viewModel.tabBars,
                    expenseCategories: viewModel.expenseCategories,
                    incomeCategories: viewModel.incomeCategories
                )
                
                FabButtonView(
                    size: 56,
                    xPos: geometry.size.width - FabConstant.xOffset,
                    yPos: geometry.size.height - FabConstant.yOffset,
                    onClick: {}
                )
            }
            .customToolbar(content: toolbarContent, onEvent: onToolbarEvent(toolbarEvent:))
            .onChange(of: viewModel.searchQuery, perform: viewModel.onSearchQueryChanged(query:))
        }
        
    }
    
    private func onToolbarEvent(toolbarEvent: ToolbarEvent) {
        
    }
}

#Preview {
    CategoriesScreen()
}
