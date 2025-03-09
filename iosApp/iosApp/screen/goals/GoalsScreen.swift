import SwiftUI
import Shared

struct GoalsScreen: View {
    
    @Binding var openDrawer: Bool
    var showDrawer: Bool = false
    @StateObject private var viewModel: GoalsViewModel = GoalsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: GoalsState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                GoalsTopbar()
                GoalsContent()
                .scrollIndicators(.hidden)
                
            }
            .frame(width: proxy.size.width, height: proxy.size.height)
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: { viewModel.handle(event: .AddGoal()) }
            )
        }
        .navigationBarBackButtonHidden(true)
        .onAppear(perform: viewModel.initData)
        .onChange(of: viewModel.uiEffect){
            observe(effect:viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func GoalsTopbar() -> some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(
                title: "Goals",
                showDrawer: showDrawer,
                onAction: onAction
            )
            OutlinedSearchTextFieldView(
                query: Binding(
                    get: { uiState.searchQuery },
                    set: {query in viewModel.handle(event: .FilterBy(query: query))}
                ),
                placeHolder: "Search goal..."
            )
            .padding(.horizontal, 16)
            Divider()
        }
    }
    
    @ViewBuilder
    private func GoalsContent() -> some View {
        ScrollView {
            VStack(spacing: 16) {
                ListItemView()
            }
            .padding(16)
        }
    }
    
    @ViewBuilder
    private func ListItemView() -> some View {
        ForEach(uiState.goals, id: \.self) { goal in
            GoalItemView(goal: goal)
                .onTapGesture { viewModel.handle(event: .NavigateToGoal(id: goal.id)) }
        }
    }
    
    private func observe(effect: GoalsEffect?) {
        guard let effect else { return }
        
        switch onEnum(of: effect) {
        case .addGoal: navigation.navigate(to: .addGoal(goalId: 0))
        case .navigateBack: navigation.navigateBack()
        case .navigateToGoal(let effect): navigation.navigate(to: .goal(id: effect.id))
        case .openDrawer: openDrawer = !openDrawer
        case .showError(let effect): print("error \(effect.message)")
        }
        
        viewModel.uiEffect = nil
    }
    
    private func onAction() {
        if showDrawer {
            viewModel.handle(event: .OpenDrawer())
        } else {
            viewModel.handle(event: .NavigateBack())
        }
    }
}

#Preview {
    GoalsScreen(openDrawer: .constant(false))
}
