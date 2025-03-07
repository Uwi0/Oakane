import SwiftUI
import Shared

struct PrimaryWalletView: View {
    
    let state: HomeState
    let onEvent: (HomeEvent) -> Void
    
    private let size: CGFloat = 30
    
    private var wallet: WalletModel {
        state.wallet
    }
    
    private var icon: String {
        wallet.iconName.asIconCategory()
    }
    
    private var color: Color {
        Color(hex: wallet.color.toColorLong())
    }
    
    private var balance: String {
        if state.isBalanceVisible {
            wallet.balance.formatted()
        } else {
            StringExtKt.maskText(wallet.balance.formatted())
        }
    }
    
    var body: some View {
        VStack(alignment: .center, spacing: 8) {
            TopContentView()
            BottomContentView()
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func TopContentView() -> some View {
        HStack(alignment: .center, spacing: 8) {
            IconView()
            Text(wallet.name).font(Typography.titleMedium)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    @ViewBuilder
    private func IconView() -> some View {
        if wallet.isDefaultIcon {
            CategoryIconView(icon: icon, color: color, size: size, padding: 8)
        } else {
            DisplayImageFileView(fileName: wallet.icon, width: size, height: size)
        }
    }
    
    @ViewBuilder
    private func BottomContentView() -> some View {
        HStack(spacing: 8) {
            VStack{
                Text("\(wallet.currency.symbol)")
                    .font(Typography.bodyMedium)
                Spacer().frame(height: 16)
            }
            
            Text(balance)
                .font(.title)
                .foregroundColor(ColorTheme.primary)
            
            Button(action: { onEvent(.ChangeBalanceVisibility())}) {
                Image(systemName: state.isBalanceVisible ? "eye" : "eye.slash")
                    .foregroundColor(ColorTheme.outline)
            }
            .buttonStyle(PlainButtonStyle())
        }
        .alignmentGuide(.firstTextBaseline) { _ in 0 }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

#Preview {
    PrimaryWalletView(state: HomeState.companion.default(), onEvent: { event in })
}
