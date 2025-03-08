import SwiftUI
import Shared

struct CategoriesScreen: View {
    
    @Binding var openDrawer: Bool
    var showDrawer: Bool = false
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
        .sheet(
            isPresented: Binding(
                get: { uiState.showSheet },
                set: { shown in viewModel.handle(event: .ShowSheet(visibility: shown))}
            ),
            onDismiss: { viewModel.handle(event: .ShowSheet(visibility: false))}
        ) {
            VStack {
                switch uiState.sheetContent {
                case .create:
                    CreateCategoryContentView(uiState: viewModel.uiState, onEvent: viewModel.handle)
                case .selectColor:
                    Text("Select Color")
                case .selectIcon:
                    SelectIconView(
                        selectedIcon: Binding(
                            get: { uiState.selectedIcon },
                            set: { icon in viewModel.handle(event: .SelectedIcon(name: icon))}
                        ),
                        selectedColor: Color(hex: uiState.selectedColor.toColorLong()),
                        onTakeImage: { image in viewModel.handle(event: .PickImage(file: image))},
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
            case .openDrawer:
                print("drawer opened")
            case .showError(let effect):
                print("error \(effect.message)")
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    CategoriesScreen(openDrawer: .constant(false))
}
