import SwiftUI

struct GoalHeaderView: View {
    
    let isVisible: Bool
    let onClick: () -> Void
    
    private let imageSize: CGFloat = 24
    
    var body: some View {
        if isVisible {
            AddGoalView()
        } else {
            SimplifiedAddGoalView()
        }
    }
    
    @ViewBuilder
    private func AddGoalView() -> some View {
        HStack(spacing: 16) {
            OutlinedCircleIcon(imageName: "star.circle", size: imageSize)
            VStack(alignment: .leading, spacing: 8) {
                Text("My Goals").font(Typography.bodyMedium)
                Text("Add New Goals")
                    .font(Typography.bodyMedium)
            }
            Spacer()
            IconButtonView(name: "plus", width: imageSize, onClick: onClick)
                .padding(.trailing, 12)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func SimplifiedAddGoalView() -> some View {
        HStack {
            Text("Add Goal")
                .font(Typography.titleMedium)
            Spacer()
            IconButtonView(name: "plus", width: 24, onClick: onClick).padding(.trailing, 12)
        }
    }
}

#Preview {
    GoalHeaderView(isVisible: true, onClick: {})
}
