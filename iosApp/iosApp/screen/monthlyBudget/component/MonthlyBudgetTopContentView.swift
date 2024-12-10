import SwiftUI
import Shared

struct MonthlyBudgetTopContentView: View {
    
    @State private var budget: Int = 0
    let onEvent: (MonthlyBudgetEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("What is your Monthly Budget ?")
                .font(Typography.titleLarge)
            Text("enter the amount of you’re budget, you can change the amount if necessary")
                .foregroundStyle(ColorTheme.outline)
                .font(Typography.bodySmall)
            OutlinedCurrencyTextFieldView(
                value: $budget,
                leadingIcon: "list.clipboard",
                onValueChange: { budget in onEvent(.Changed(amount: budget)) }
            )
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

#Preview {
    MonthlyBudgetTopContentView(onEvent: { _ in })
}
