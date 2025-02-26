import SwiftUI
import Shared

internal struct ReportBudgetContentView: View {
    
    let item: MonthlyBudgetOverView
    
    private var currency: Currency {
        item.currency
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Budget: \(item.limit.toFormatCurrency(currency: currency))")
                .font(Typography.titleMedium)
                .foregroundStyle(ColorTheme.outline)
            
            ProgressIndicatorView(value: item.progress)
            
            Text("Spent: \(item.spent.toFormatCurrency(currency: currency))")
                .font(Typography.labelMedium)
                .foregroundStyle(ColorTheme.outline)
            
            Divider()
            
            bottomContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder func bottomContentView() -> some View {
        HStack(spacing: 8) {
            BudgetItemView(amount: item.totalIncome, isExpense: false, currency: currency)
            Spacer()
            Divider().frame(height: 48)
            Spacer()
            BudgetItemView(amount: item.totalExpense, isExpense: true, currency: currency)
        }
    }
}
