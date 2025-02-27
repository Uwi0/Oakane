import SwiftUI
import Shared

struct TransferLogItemView: View {
    
    let item: WalletTransferModel
    
    private var foregroundColor: Color {
        item.type == .incoming ? ColorTheme.primary : ColorTheme.error
    }
    
    private var iconColor: Color {
        item.type == .incoming ? ColorTheme.onPrimary : ColorTheme.onError
    }
    
    private var title: String {
        item.type == .incoming ? "Received from \(item.name)" : "Sent to \(item.name)"
    }
    
    private var amount: String {
        item.amount.toFormatCurrency(currency: item.currency)
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            TranferLogIconView()
            TranferLogContentView()
            TranferLogTrailingContent()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func TranferLogIconView() -> some View {
        Circle()
            .frame(width: 48, height: 48)
            .foregroundStyle(foregroundColor)
            .overlay {
                Image(systemName: "arrow.left.arrow.right")
                    .foregroundStyle(iconColor)
                    .fontWeight(.bold)
            }
    }
    
    @ViewBuilder
    private func TranferLogContentView() -> some View {
        VStack(alignment: .leading,spacing: 8) {
            Text(title).font(Typography.titleMedium)
            Text(amount).font(Typography.titleLarge).foregroundStyle(foregroundColor)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    @ViewBuilder
    private func TranferLogTrailingContent() -> some View {
        VStack(alignment: .trailing, spacing: 8) {
            Image(systemName: "info.circle")
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(ColorTheme.outline)
            Text(item.formattedDate)
                .font(Typography.labelMedium)
        }
    }
}

#Preview {
    let defaultValue = WalletTransferModel.companion.default()
    TransferLogItemView(item: defaultValue)
}
