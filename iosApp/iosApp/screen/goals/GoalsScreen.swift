import SwiftUI
import Shared

struct GoalsScreen: View {
    
    @StateObject private var viewModel: GoalsViewModel = GoalsViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: GoalsState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                GoalsTopAppBar(onEvent: viewModel.handle(event:))
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
                .onTapGesture {
                    viewModel.handle(event: .NavigateToGoal(id: goal.id))
                }
        }
    }
    
    private func observe(effect: GoalsEffect?) {
        if let safEffect = effect {
            switch onEnum(of: safEffect) {
            case .addGoal:
                navigation.navigate(to:.addGoal(goalId: 0))
            case .navigateBack:
                navigation.navigateBack()
            case .navigateToGoal(let goalsEffect):
                navigation.navigate(to: .goal(goalId: goalsEffect.id))
            case .openDrawer:
                print("open drawer")
            case .showError(let effect):
                print("error \(effect.message)")
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    GoalsScreen()
}
