import SwiftUI
import Shared

struct GoalItemView: View {
    let goal: GoalModel
    var body: some View {
        HStack(spacing: 16) {
            Image(ImageConstants.defaultImage)
                .resizable()
                .frame(width: 64, height: 64)
                .clipShape(Circle())
            
            VStack(alignment: .leading,spacing: 4) {
                Text(goal.goalName)
                    .font(Typography.titleSmall)
                Text("Rp \(goal.amount.formatted())")
                    .font(Typography.titleSmall)
                ProgressIndicatorView(value: goal.progress)
                HStack {
                    Text("Rp \(goal.savedMoney.formatted())/\(goal.progress.formatted())%")
                        .font(Typography.bodySmall)
                        .foregroundStyle(ColorTheme.outline)
                    Spacer()
                    Text("\(goal.endDate.formatDateWith(pattern: "dd MMM yyyy"))")
                        .font(Typography.bodySmall)
                        .foregroundStyle(ColorTheme.outline)
                }
            }
            
            
            
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
