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
                VStack(spacing: 16) {
                    CardGoalView(uiState: uiState)
                    CardTimeView(uiState: uiState)
                    CardNoteView(note: uiState.note)
                    Spacer()
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
            }
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: {
                    viewModel.handle(event: .Dialog(shown: true, content: .updateAmount))
                }
            )
            if uiState.isDialogShown {
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

private struct GoalDialogView: View {
    
    let uiState: GoalState
    let onEvent: (GoalEvent) -> Void
    
    var body: some View {
        PopUpDialog(
            onDismiss: { _ in
                onEvent(.Dialog(shown: false, content: .updateAmount))
            }
        ) {
            switch uiState.dialogContent {
            case .updateAmount:
                DialogAddGoalSavingView(onEvent: onEvent)
            case .deleteGoal:
                DialogDeleteGoalConfirmationView(onEvent: onEvent)
            }
            
        }
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
                navigateBack: { onEvent(.NavigateBack()) }
            )
            Divider()
        }
    }
}

#Preview {
    GoalScreen(goalId: 0)
}
