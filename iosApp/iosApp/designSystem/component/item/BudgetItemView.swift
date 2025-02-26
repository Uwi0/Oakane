import SwiftUI
import Shared

struct BudgetItemView: View {
    
    let amount: Double
    let isExpense: Bool
    let currency: Currency
    
    private var icon: String {
        isExpense ? "arrow.down" : "arrow.up"
    }
    
    private var title: String {
        isExpense ? "Total Expense" : "Total Income"
    }
    
    private var color: Color {
        isExpense ? ColorTheme.error : ColorTheme.primary
    }
    
    var body: some View {
        HStack(alignment: .center,spacing: 8) {
            Image(systemName: icon)
                .font(Typography.labelMedium)
                .foregroundStyle(Color.white)
                .padding(8)
                .background(Circle().fill(color))
            
            VStack(alignment: .leading) {
                Text(title)
                    .font(Typography.titleSmall)
                    .foregroundStyle(ColorTheme.outline)
                
                Text("\(amount.toFormatCurrency(currency: currency))")
                    .font(Typography.bodySmall)
            }
        }
    }
}

#Preview {
    BudgetItemView(amount: 2.0, isExpense: true, currency: .usd)
}
