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
    
    private var category: CategoryModel {
        transaction.category
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            let dateCreated = transaction.dateCreated.formatDateWith(pattern: "dd MMM yyyy")
            RowTextView(title: "Wallet", value: wallet.name, icon: WalletIcon)
            Divider()
            RowTextView(title: "Category", value: transaction.category.name, icon: CategoryIcon)
            Divider()
            RowTextView(title: "Type", value: transaction.type.name, icon: ExpenseIcon)
            Divider()
            RowTextView(title: "Date", value: dateCreated, icon: { IconDefault(name: "calendar") })
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
            .resizable()
            .foregroundStyle(ColorTheme.outline)
            .font(.system(size: 24, weight: .medium))
            .frame(width: 24, height: 24)
    }
    
    @ViewBuilder
    private func ExpenseIcon() -> some View {
        TransactionTypeIconView(transactionType: transaction.type, iconSize: 24)
    }
    
    @ViewBuilder
    private func FileIcon(fileName: String, icon: CategoryIconName, isDefault: Bool) -> some View {
        if isDefault {
            IconDefault(name: icon.asIconCategory())
        } else {
            DisplayImageFileView(fileName: fileName, width: 24, height: 24)
        }
    }
    
    
    @ViewBuilder
    private func WalletIcon() -> some View {
        FileIcon(fileName: wallet.icon, icon: wallet.iconName, isDefault: wallet.isDefaultIcon)
    }
    
    @ViewBuilder
    private func CategoryIcon() -> some View {
        FileIcon(fileName: category.icon, icon: category.iconName, isDefault: category.isDefault)
    }
}


#Preview {
    TransactionDetailContentView(state: TransactionState.companion.default())
}
