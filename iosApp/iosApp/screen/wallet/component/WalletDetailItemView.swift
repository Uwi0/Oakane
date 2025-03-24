import SwiftUI
import Shared

struct WalletDetailItemView: View {
    
    let state: WalletState
    
    private var wallet: WalletItemModel {
        state.wallet
    }
    
    private var balance: String {
        wallet.balance
            .toFormatCurrency(currency: wallet.currency)
            .replacingOccurrences(of: "\(wallet.currency.symbol)", with: "")
        
    }
    
    private var walletColor: Color {
        Color(hex: wallet.color.toColorLong())
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TopContent()
            Divider()
            BottomContent()
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func TopContent() -> some View {
        VStack(alignment: .leading) {
            HStack {
                SelectedIconView(imageName: wallet.icon, icon: wallet.iconName, color: walletColor)
                Text(wallet.name).font(Typography.titleMedium)
            }
            HStack(alignment: .top) {
                Text(wallet.currency.symbol)
                    .font(Typography.labelLarge)
                Text(balance)
                    .font(Typography.headlineMedium)
                    .foregroundStyle(ColorTheme.primary)
            }
        }
    }
    
    @ViewBuilder
    private func BottomContent() -> some View {
        HStack {
            TransactionIconView(amount: wallet.income, isExpense: false, currency: wallet.currency)
            Spacer()
            Divider().frame(height: 48)
            Spacer()
            TransactionIconView(amount: wallet.expense, isExpense: true, currency: wallet.currency)
        }
    }
    
}

#Preview {
    let defaultValue = WalletState.companion.default()
    WalletDetailItemView(state: defaultValue)
}
