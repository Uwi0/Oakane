import SwiftUI
import Shared

struct CategoriesScreen: View {
    
    @StateObject private var viewModel = CategoriesViewModel()
    let toolbarContent = ToolBarContent(title: "Categories")
    
    var bottomSheetSize: PresentationDetent {
        switch viewModel.uiState.sheetContent {
        case .create: return .medium
        case .selectColor: return .large
        case .selectIcon: return .fraction(0.9)
        }
    }
    
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                ColorTheme.surface
                    .ignoresSafeArea()
                CategoriesContentView(
                    selectedTab: $viewModel.uiState.selectedTab,
                    searchQuery: $viewModel.uiState.searchQuery,
                    tabBars: viewModel.tabBars,
                    expenseCategories: viewModel.expenseCategories,
                    incomeCategories: viewModel.incomeCategories
                )
                .onChange(of: viewModel.uiState.searchQuery, perform: viewModel.onSearchQueryChanged(query:))
                
                FabButtonView(
                    size: 56,
                    xPos: geometry.size.width - FabConstant.xOffset,
                    yPos: geometry.size.height - FabConstant.yOffset,
                    onClick: { viewModel.uiState.showSheet.toggle() }
                )
            }
            .customToolbar(content: toolbarContent, onEvent: onToolbarEvent(toolbarEvent:))
            .sheet(isPresented: $viewModel.uiState.showSheet) {
                VStack {
                    switch viewModel.uiState.sheetContent {
                    case .create:
                        CreateCategoryContentView(uiState: viewModel.uiState, onEvent: viewModel.onEvent)
                    case .selectColor:
                        Text("Select Color")
                    case .selectIcon:
                        Text("Select Icon")
                    }
                }
                .presentationDetents([.medium])
                .presentationDragIndicator(.visible)
            }
        }
        
    }
    
    private func onToolbarEvent(toolbarEvent: ToolbarEvent) {
        
    }
}

#Preview {
    CategoriesScreen()
}
