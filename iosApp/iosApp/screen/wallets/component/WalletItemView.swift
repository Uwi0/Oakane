import SwiftUI
import Shared

struct WalletItemView: View {
    
    let wallet: WalletItemModel
    let onSelectWallet: () -> Void
    
    private var formattedBalance: String {
        wallet.balance.toFormatIDR()
    }
    
    var body: some View {
        VStack {
            TopContentView(wallet: wallet, onSelectWalled: onSelectWallet)
            Text(formattedBalance).font(Typography.headlineMedium)
            Divider().scaleEffect(y: 2.5)
            BottomContentView(wallet: wallet)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

fileprivate struct TopContentView: View {
    
    let wallet: WalletItemModel
    let onSelectWalled: () -> Void
    
    var body: some View {
        HStack(spacing: 8) {
            SelectedIconView(
                imageName: wallet.icon,
                icon: wallet.iconName,
                color: wallet.color.toColorLong()
            )
            Text(wallet.name)
                .font(Typography.titleMedium)
            Spacer()
            OutlinedCheckmarkRadioButton(selected: wallet.isSelected, onClick: onSelectWalled)
        }
    }
}



fileprivate struct BottomContentView: View {
    
    let wallet: WalletItemModel
    
    var body: some View {
        HStack(spacing: 10) {
            BalanceContent(
                title: "Expense this month",
                amount: wallet.expense,
                color: ColorTheme.error
            )
            Divider().scaleEffect(x: 2.5)
            BalanceContent(
                title: "Income this month",
                amount: wallet.income,
                color: ColorTheme.primary
            )
        }
        .padding(.top, 8)
    }
}

fileprivate struct BalanceContent: View {
    
    let title: String
    let amount: Double
    let color: Color
    
    private var formattedAmount: String {
        amount.toIDRCurrency()
    }
    
    var body: some View {
        VStack(alignment: .center, spacing: 8) {
            Text(title)
                .foregroundStyle(color)
                .font(Typography.labelMedium)
            Text("Rp \(formattedAmount)")
        }
        .frame(minWidth: 120)
    }
}
