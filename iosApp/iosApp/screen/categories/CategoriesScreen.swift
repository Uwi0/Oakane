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
    
    private var uiState: CategoriesState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { geometry in
            ColorTheme.surface
                .ignoresSafeArea()
            CategoriesContentView(
                selectedTab: $viewModel.uiState.selectedTab,
                searchQuery: $viewModel.uiState.searchQuery,
                uiState: uiState,
                onEvent: viewModel.handle(event:)
            )
            .onChange(of: uiState.searchQuery){
                viewModel.handle(event: .Search(query: uiState.searchQuery))
            }
            
            FabButtonView(
                size: FabConstant.size,
                xPos: geometry.size.width - FabConstant.xOffset,
                yPos: geometry.size.height - FabConstant.yOffset,
                onClick: { viewModel.handle(event: .ShowSheet(visibility: true)) }
            )
        }
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect){ observe(effect: viewModel.uiEffect) }
        .sheet(isPresented: $viewModel.uiState.showSheet, onDismiss: { viewModel.handle(event: .ShowSheet(visibility: false))}) {
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
    
    
    private func observe(effect: CategoriesEffect?){
        if let safeEffect = effect {
            switch onEnum(of: safeEffect){
            case .hideSheet:
                print("Hide Sheet")
            case .navigateBack:
                navigation.navigateBack()
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    CategoriesScreen()
}
