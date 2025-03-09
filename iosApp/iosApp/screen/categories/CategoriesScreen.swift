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
            
            CategoriesContentView()
            
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
                case .create:CreateCategoryContentView(
                    uiState: viewModel.uiState,
                    onEvent: viewModel.handle
                )
                case .selectColor: Text("Select Color")
                case .selectIcon: SelectIconCategoryView()
                    
                }
            }
            .presentationDetents([bottomSheetSize])
            .presentationDragIndicator(.visible)
        }
    }
    
    @ViewBuilder
    private func CategoriesContentView() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Categories",
                showDrawer: showDrawer,
                onAction: onAction
            )
            OutlinedSearchTextFieldView(
                query: Binding(
                    get: { uiState.searchQuery },
                    set: { viewModel.handle(event: .Search(query: $0))
                    }
                ),
                placeHolder: "Search Categories..."
            )
            .padding(.horizontal, 16)
            TabBarView(
                currentTab: Binding(
                    get: { uiState.selectedTab },
                    set: { index in viewModel.handle(event: .ChangeTab(index: Int32(index))) }
                ),
                tabBarOptions: TransactionType.allCases.map{ type in type.name }
            )
            TabContentView()
        }
    }
    
    @ViewBuilder
    private func TabContentView() -> some View {
        TabView(
            selection: Binding(
                get: { uiState.selectedTab },
                set: { index in viewModel.handle(event: .ChangeTab(index: Int32(index))) }
            )
        ) {
            CategoriesView(
                categories: uiState.categories.filter{ category in category.type == .income },
                onClick: { category in viewModel.handle(event: .OnTapped(category: category)) }
            )
            .tag(0)
            
            CategoriesView(
                categories: uiState.categories.filter{ category in category.type == .expense },
                onClick: { category in viewModel.handle(event: .OnTapped(category: category)) }
            )
            .tag(1)
            
        }
        .tabViewStyle(.page(indexDisplayMode: .never))
    }
    
    @ViewBuilder
    private func SelectIconCategoryView() -> some View {
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
    
    private func onAction() {
        if showDrawer {
            viewModel.handle(event: .OpenDrawer())
        } else {
            viewModel.handle(event: .NavigateBack())
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
                openDrawer = !openDrawer
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
