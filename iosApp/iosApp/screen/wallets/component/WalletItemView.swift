import SwiftUI
import Shared

struct WalletItemView: View {
    
    let wallet: WalletItemModel
    let onSelectWallet: () -> Void
    
    private var formattedBalance: String {
        wallet.balance.toFormatCurrency(currency: wallet.currency)
    }
    
    private var formattedColor: Color {
        Color(hex: wallet.color.toColorLong())
    }
    
    var body: some View {
        VStack {
            TopContentView()
            Text(formattedBalance).font(Typography.headlineMedium)
            Divider().scaleEffect(y: 2.5)
            BottomContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func TopContentView() -> some View {
        HStack(spacing: 8) {
            SelectedIconView(
                imageName: wallet.icon,
                icon: wallet.iconName,
                color: formattedColor
            )
            Text(wallet.name)
                .font(Typography.titleMedium)
            Spacer()
            OutlinedCheckmarkRadioButton(selected: wallet.isSelected, onClick: onSelectWallet)
        }
    }
    
    @ViewBuilder
    private func BottomContentView() -> some View {
        HStack(spacing: 10) {
            BalanceContent(
                title: "Expense this month",
                amount: wallet.expense,
                currency: wallet.currency,
                color: ColorTheme.error
            )
            Divider().scaleEffect(x: 2.5)
            BalanceContent(
                title: "Income this month",
                amount: wallet.income,
                currency: wallet.currency,
                color: ColorTheme.primary
            )
        }
        .padding(.top, 8)
    }
    
    @ViewBuilder
    private func BalanceContent(
        title: String,
        amount: Double,
        currency: Currency,
        color: Color
    ) -> some View {
        
        let formattedAmount = amount.toFormatCurrency(currency: currency)
        VStack(alignment: .center, spacing: 8) {
            Text(title)
                .foregroundStyle(color)
                .font(Typography.labelMedium)
            Text(formattedAmount)
        }
        .frame(minWidth: 120)
    }
}

