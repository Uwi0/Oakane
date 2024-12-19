import SwiftUI
import Shared

struct CategoriesScreen: View {
    
    @StateObject private var viewModel = CategoriesViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    let toolbarContent = ToolBarContent(title: "Categories")
    
    private var bottomSheetSize: PresentationDetent {
        switch viewModel.uiState.sheetContent {
        case .create: return .fraction(0.55)
        case .selectColor: return .large
        case .selectIcon: return .fraction(0.9)
        }
    }
    
    private var sheetDisable: Bool {
        viewModel.uiState.sheetContent == CategoriesSheetContent.create
    }
    
    private var searchQuery: String {
        viewModel.uiState.searchQuery
    }
    
    private var uiState: CategoriesState {
        viewModel.uiState
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
                    incomeCategories: viewModel.incomeCategories,
                    onEvent: viewModel.handle
                )
                .onChange(of: searchQuery){
                    viewModel.handle(event: .Search(query: searchQuery))
                }
                
                FabButtonView(
                    size: FabConstant.size,
                    xPos: geometry.size.width - FabConstant.xOffset,
                    yPos: geometry.size.height - FabConstant.yOffset,
                    onClick: { viewModel.handle(event: .ShowSheet(visibility: true)) }
                )
            }
            .navigationBarBackButtonHidden(true)
            .customToolbar(content: toolbarContent, onEvent: onToolbarEvent(toolbarEvent:))
            .sheet(isPresented: $viewModel.uiState.showSheet) {
                VStack {
                    switch uiState.sheetContent {
                    case .create:
                        CreateCategoryContentView(uiState: viewModel.uiState, onEvent: viewModel.handle)
                    case .selectColor:
                        Text("Select Color")
                    case .selectIcon:
                        SelectIconView(
                            selectedIcon: uiState.selectedIcon,
                            selectedColor: uiState.selectedColor,
                            onPickIcon: { icon in viewModel.handle(event: .SelectedIcon(name: icon))},
                            onTakImage: { image in viewModel.handle(event: .PickImage(file: image))},
                            onConfirm: { viewModel.handle(event: .ConfirmIcon())}
                        )
                    }
                }
                .presentationDetents([bottomSheetSize])
                .presentationDragIndicator(.visible)
            }
        }
        
    }
    
    private func onToolbarEvent(toolbarEvent: ToolbarEvent) {
        navigation.navigateBack()
    }
}

#Preview {
    CategoriesScreen()
}
