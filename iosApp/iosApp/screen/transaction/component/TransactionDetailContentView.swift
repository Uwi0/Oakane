import SwiftUI
import Shared

struct TransactionDetailContentView: View {
    let transaction: TransactionModel
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            ColumnTextView(title: "Date", value: transaction.dateCreated.toDateWith(format: "dd MMM yyyy"))
            ColumnTextView(title: "Category", value: transaction.category.name)
            ColumnTextView(title: "Type", value: transaction.type.name)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
        
    }
}

private struct ColumnTextView: View {
    let title: String
    let value: String
    
    var body: some View {
        VStack(alignment: .leading,spacing: 4) {
            Text(title)
                .font(Typography.bodyMedium)
                .foregroundStyle(ColorTheme.primary)
            Text(value)
                .font(Typography.titleSmall)
                .foregroundStyle(ColorTheme.outline)
        }
    }
}

#Preview {
    let transaction = TransactionModelKt.dummyValue()
    TransactionDetailContentView(transaction: transaction)
}
