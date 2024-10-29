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
                    tabBars: viewModel.tabBars,
                    expenseCategories: viewModel.expenseCategories,
                    incomeCategories: viewModel.incomeCategories
                )
                
                FabButtonView(
                    size: 56,
                    xPos: geometry.size.width - 50,
                    yPos: geometry.size.height - 50,
                    onClick: {}
                )
            }
            .customToolbar(content: toolbarContent, onEvent: onToolbarEvent(toolbarEvent:))
        }
        
    }
    
    private func onToolbarEvent(toolbarEvent: ToolbarEvent) {
        
    }
}

#Preview {
    CategoriesScreen()
}
