import SwiftUI
import Shared

internal struct ReportBudgetContentView: View {
    
    let item: MonthlyBudgetOverView
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Budget: \(item.limit.toIDRCurrency())")
                .font(Typography.titleMedium)
                .foregroundStyle(ColorTheme.outline)
            
            ProgressIndicatorView(value: item.progress)
            
            Text("Spent: \(item.spent.toIDRCurrency())")
                .font(Typography.labelMedium)
                .foregroundStyle(ColorTheme.outline)
            
            Divider()
            
            bottomContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder func bottomContentView() -> some View {
        HStack(spacing: 8) {
            budgetItemView(amount: item.totalIncome, isExpense: false)
            Spacer()
            Divider().frame(height: 48)
            Spacer()
            budgetItemView(amount: item.totalExpense, isExpense: true)
        }
    }
    
    @ViewBuilder func budgetItemView(amount: Double, isExpense: Bool) -> some View {
        let icon = isExpense ? "arrow.down" : "arrow.up"
        let title = isExpense ? "Total Expense" : "Total Income"
        let color = isExpense ? ColorTheme.error : ColorTheme.primary
        
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
                
                Text("\(amount.toIDRCurrency())")
                    .font(Typography.bodySmall)
            }            
        }
    }
}
