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
            CardContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func CardContentView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(uiState.title)
                .font(Typography.titleMedium)
                .foregroundStyle(ColorTheme.outline)
            SavingProgressView()
            ProgressIndicatorView(value: uiState.progress)
        }
    }
    
    @ViewBuilder
    private func SavingProgressView() -> some View {
        HStack(spacing: 8) {
            CardContentWithIconView(
                icon: "wallet.bifold",
                title: "Balance",
                content: uiState.currentAmount
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
    let uiState = GoalState()
    CardGoalView(uiState: uiState)
}
