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
    private let size: CGFloat = 30
    
    var body: some View {
        HStack(spacing: 8) {
            SelectedIconView(
                imageName: wallet.icon,
                icon: wallet.iconName,
                color: wallet.color.toColorInt(),
                size: size,
                padding: 8
            )
            Text(wallet.name)
                .font(Typography.bodyMedium)
            Spacer()
            OutlinedCheckmarkRadioButton(selected: wallet.isSelected, onClick: onSelectWalled)
        }
    }
}

fileprivate struct OutlinedCheckmarkRadioButton: View {
    let selected: Bool
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .stroke(
                        selected ? ColorTheme.primary : ColorTheme.outline,
                        lineWidth: 2
                    )
                    .frame(width: 24, height: 24)

                if selected {
                    Image(systemName: "checkmark")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(ColorTheme.primary)
                        .frame(width: 12, height: 12)
                }
            }
        }
        .buttonStyle(PlainButtonStyle())
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
