import SwiftUI
import Shared

struct GoalSavingItemView: View {
    
    let item: GoalSavingModel
    let currency: Currency
    var isInLog: Bool = false
    
    private var transactionType: TransactionType {
        isInLog ? .expense : .income
    }
    
    private var textColor: Color {
        isInLog ? ColorTheme.error : ColorTheme.primary
    }
    
    private var note: String {
        item.note.isEmpty ? "-" : item.note
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            TransactionTypeIconView(transactionType: transactionType, iconSize: 48)
            DetailGoalSavingView()
            DateCreatedView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func DetailGoalSavingView() -> some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(item.amount.toFormatCurrency(currency: currency))
                .foregroundStyle(textColor)
                .font(Typography.titleLarge)
            Text("note: \(item.note)")
                .foregroundStyle(ColorTheme.outline)
                .font(Typography.labelMedium)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    @ViewBuilder
    private func DateCreatedView() -> some View {
        Text(item.formattedDateCreated)
            .font(Typography.titleMedium)
    }
}

#Preview {
    let item = GoalSavingModel.companion.default()
    GoalSavingItemView(item: item, currency: .usd)
}
