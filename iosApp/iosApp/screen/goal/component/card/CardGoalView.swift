import SwiftUI
import Shared

struct CardGoalView: View {
    
    let uiState: GoalState
    private let size: CGFloat = 54
    
    var body: some View {
        HStack(spacing: 8) {
            DisplayImageFileView(
                defaultImage: ImageConstants.defaultImage,
                fileName: uiState.fileName,
                width: size,
                height: size
            )
            CardContentView(uiState: uiState)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

private struct CardContentView: View {
    
    let uiState: GoalState
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(uiState.title)
                .font(Typography.titleMedium)
                .foregroundStyle(ColorTheme.outline)
            SavingProgressView(uiState: uiState)
            ProgressIndicatorView(value: uiState.progress)
        }
    }
}

private struct SavingProgressView: View {
    
    let uiState: GoalState
    
    var body: some View {
        HStack(spacing: 8) {
            CardContentWithIconView(
                icon: "wallet.bifold",
                title: "Balance",
                content: uiState.savedAmount
            )
            .frame(maxWidth: .infinity, alignment: .leading)
            Divider()
                .frame(width: 3, height: 30)
            CardContentWithIconView(
                icon: "flag",
                title: "Target",
                content: uiState.targetAmount
            )
            .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}

#Preview {
    let uiState = GoalState(fileName: "", title: "Hello world")
    CardGoalView(uiState: uiState)
}
