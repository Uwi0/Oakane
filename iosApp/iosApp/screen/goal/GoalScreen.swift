import SwiftUI

struct GoalScreen: View {
    
    let goalId: Int64
    
    @StateObject private var viewModel: GoalViewModel = GoalViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: GoalState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                ColorTheme.surface.ignoresSafeArea()
                VStack {
                    ToolbarView(onClick: { navigation.navigateBack() })
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
                    PopUpDialog(
                        onDismiss: { dimiss in
                            viewModel.handle(event: .Dialog(shown: false, content: .updateAmount))
                        }
                    ) {
                        DialogAddGoalSavingView(onEvent: viewModel.handle(event:))
                    }
                }
            }
        }
        .navigationBarBackButtonHidden(true)
        .onAppear {
            viewModel.initializeData(goalId: goalId)
        }
    }
    
    private func toolbar(event: ToolbarEvent) {
        navigation.navigateBack()
    }
}

private struct ToolbarView: View {
    
    let onClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            NavigationTopAppbar(title: "My Goal", navigateBack: onClick)
            Divider()
        }
    }
}

#Preview {
    GoalScreen(goalId: 0)
}
