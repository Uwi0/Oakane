import SwiftUI
import Shared

struct GoalItemView: View {
    let goal: GoalModel
    var body: some View {
        HStack(spacing: 16) {
            DisplayImageFileView(
                fileName: goal.fileName,
                width: 64,
                height: 64
            )
            
            VStack(alignment: .leading,spacing: 4) {
                Text(goal.goalName)
                    .font(Typography.titleSmall)
                Text("\(goal.amount.toFormatCurrency(currency: goal.currency))")
                    .font(Typography.titleSmall)
                ProgressIndicatorView(value: goal.progress)
                HStack {
                    Text("\(goal.savedMoney.toFormatCurrency(currency: goal.currency))/\(goal.progress.formatted())%")
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
