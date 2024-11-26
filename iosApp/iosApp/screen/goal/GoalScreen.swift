import SwiftUI

struct GoalScreen: View {
    
    let goalId: Int64
    
    @StateObject private var viewModel: GoalViewModel = GoalViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    private let toolbarContent = ToolBarContent(title: "My Goal")
    private var uiState: GoalState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                ColorTheme.surface.ignoresSafeArea()
                VStack {
                    ToolbarView()
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
                        navigation.navigate(to: .addTransaction(transactionId: 0))
                    }
                )
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
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack(spacing: 16) {
                Image(systemName: "arrow.left")
                    .fontWeight(.semibold)
                    .frame(width: 24, height: 24)
                Text("Add Goal")
                    .font(Typography.titleLarge)
            }
            .padding(16)
            Divider()
        }
    }
}

#Preview {
    GoalScreen(goalId: 0)
}
