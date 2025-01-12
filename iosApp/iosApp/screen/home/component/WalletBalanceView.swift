import SwiftUI
import Shared

struct WalletBalanceView: View {
    
    let wallet: WalletModel
    
    var body: some View {
        VStack(alignment: .center, spacing: 8) {
            TopContentView(wallet: wallet)
            BottomContentView(wallet: wallet)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

fileprivate struct TopContentView: View {
    let wallet: WalletModel
    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            IconView(wallet: wallet)
            Text(wallet.name).font(Typography.titleMedium)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

fileprivate struct IconView: View {
    let wallet: WalletModel
    
    private let size: CGFloat = 30
    private var icon: String {
        wallet.iconName.asIconCategory()
    }
    
    private var color: Color {
        Color(hex: wallet.color.toColorLong())
    }
    
    var body: some View {
        if wallet.isDefaultIcon {
            CategoryIconView(icon: icon, color: color, size: size, padding: 8)
        } else {
            DisplayImageFileView(fileName: wallet.icon, width: size, height: size)
        }
    }
}

fileprivate struct BottomContentView: View {
    let wallet: WalletModel
    
    private var balance: String {
        wallet.balance.formatted()
    }
    
    var body: some View {
        HStack(spacing: 8) {
            VStack{
                Text("Rp")
                    .font(.caption)
                    .alignmentGuide(.firstTextBaseline) { _ in 0 }
                Spacer().frame(height: 16)
            }
            
            
            Text(balance)
                .font(.title)
                .foregroundColor(ColorTheme.primary)
            
            Button(action: {}) {
                Image(systemName: "eye")
                    .foregroundColor(ColorTheme.outline)
            }
            .buttonStyle(PlainButtonStyle())
        }
        .alignmentGuide(.firstTextBaseline) { _ in 0 }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}
