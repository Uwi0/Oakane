import SwiftUI
import Shared

struct TransactionDetailContentView: View {
    
    let state: TransactionState
    
    private var transaction: TransactionModel {
        state.transaction
    }
    
    private var wallet: WalletModel {
        state.wallet
    }
    

    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            let dateCreated = transaction.dateCreated.formatDateWith(pattern: "dd MMM yyyy")
            RowTextView(title: "Wallet", value: wallet.name)
            Divider()
            RowTextView(title: "Category", value: transaction.category.name)
            Divider()
            RowTextView(title: "Type", value: transaction.type.name, icon: ExpenseIcon)
            Divider()
            RowTextView(title: "Date", value: dateCreated, icon: {IconDefault(name: "calendar")})
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func RowTextView(
        title: String,
        value: String,
        icon: () -> some View = { EmptyView() }
    ) -> some View {
        HStack {
            Text(title)
                .font(Typography.bodyMedium)
                .foregroundStyle(ColorTheme.primary)
            Spacer()
            HStack {
                Text(value)
                    .font(Typography.titleSmall)
                    .foregroundStyle(ColorTheme.outline)
                icon()
            }
            
        }
    }
    
    @ViewBuilder
    private func IconDefault(name: String) -> some View {
        Image(systemName: name)
            .foregroundStyle(ColorTheme.outline)
            .font(.system(size: 24, weight: .medium))
    }
    
    @ViewBuilder
    private func ExpenseIcon() -> some View {
        TransactionTypeIconView(transactionType: transaction.type, iconSize: 24)
    }
    
}


#Preview {
    let transaction = TransactionModelKt.dummyValue()
    TransactionDetailContentView(state: TransactionState())
}
