import SwiftUI
import Shared

struct GoalScreen: View {
    
    let goalId: Int64
    
    @StateObject private var viewModel: GoalViewModel = GoalViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: GoalState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                ToolbarView(onEvent: viewModel.handle(event:))
                GoalContentView()
            }
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: {
                    viewModel.handle(event: .Dialog(shown: true, content: .updateAmount))
                }
            )
            if uiState.dialogShown {
                GoalDialogView(
                    uiState: uiState,
                    onEvent: viewModel.handle(event:)
                )
            }
        }
        .navigationBarBackButtonHidden(true)
        .onAppear {
            viewModel.initializeData(goalId: goalId)
        }
        .onChange(of: viewModel.uiEffect){
            observe(effect:viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func GoalContentView() -> some View {
        VStack(spacing: 16) {
            GoalTopContentView()
            GoalBottomContentView()
        }
    }
    
    @ViewBuilder
    private func GoalTopContentView() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            CardGoalView(uiState: uiState)
            CardTimeView(uiState: uiState)
            CardNoteView(note: uiState.note)
            Text("Log Saving").font(Typography.titleMedium)
        }
        .padding(.top, 24)
        .padding(.horizontal, 16)
    }
    
    @ViewBuilder
    private func GoalBottomContentView() -> some View {
        ScrollView {
            VStack(spacing: 16) {
                GoalSavingsView()
            }
            .padding(.top, 16)
            .padding(.bottom, 24)
        }
        .scrollIndicators(.hidden)
    }
    
    @ViewBuilder
    private func GoalSavingsView() -> some View {
        ForEach(uiState.goalSavings, id: \.id) { saving in
            GoalSavingItemView(item: saving, currency: uiState.currency)
        }
        .padding(.horizontal, 16)
    }
    
    private func observe(effect: GoalEffect?){
        if let safeEffect = effect {
            switch onEnum(of: safeEffect) {
            case .navigateBack:
                navigation.navigateBack()
            case .showError(let goalEffect):
                print(goalEffect.message)
            case .updateGoalBy(let goalEffect):
                navigation.navigate(to: .addGoal(goalId: goalEffect.id))
            }
        }
        viewModel.uiEffect = nil
    }
}


private struct ToolbarView: View {
    
    let onEvent: (GoalEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            NavigationTopAppbar(
                title: "My Goal",
                actionContent: {
                    Image(systemName: "pencil")
                        .frame(width: 24, height:  24)
                        .onTapGesture {
                            onEvent(.UpdateGoal())
                        }
                    
                    Image(systemName: "trash")
                        .frame(width: 24, height:  24)
                        .onTapGesture {
                            onEvent(.Dialog(shown: true, content: .deleteGoal))
                        }
                },
                onAction: { onEvent(.NavigateBack()) }
            )
            Divider()
        }
    }
}

#Preview {
    GoalScreen(goalId: 0)
}
