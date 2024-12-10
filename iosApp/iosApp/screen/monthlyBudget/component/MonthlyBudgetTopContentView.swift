import SwiftUI
import Shared

struct MonthlyBudgetTopContentView: View {
    
    @Binding private var budget: Int
    private let onEvent: (MonthlyBudgetEvent) -> Void
    
    init(budget: Binding<Int>, onEvent: @escaping (MonthlyBudgetEvent) -> Void) {
        self._budget = budget
        self.onEvent = onEvent
    }
    
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
    MonthlyBudgetTopContentView(budget: .constant(0),onEvent: { _ in })
}
