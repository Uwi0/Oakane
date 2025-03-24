import SwiftUI
import Shared

struct TransactionIconView: View {
    
    let amount: Double
    let isExpense: Bool
    let currency: Currency
    
    
    private var title: String {
        isExpense ? "Total Expense" : "Total Income"
    }
    
    private var transactionType: TransactionType {
        isExpense ? .expense : .income
    }
    
    var body: some View {
        HStack(alignment: .center,spacing: 8) {
            TransactionTypeIconView(transactionType: transactionType, iconSize: 32)
            
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
    TransactionIconView(amount: 2.0, isExpense: true, currency: .usd)
}
