import SwiftUI
import Shared

struct WalletItemView: View {
    
    let wallet: WalletItemModel
    
    var body: some View {
        VStack {
            TopContentView(wallet: wallet)
            Text("Rp 20.000.000")
                .font(Typography.headlineMedium)
            Divider()
            HStack{
                
            }
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

fileprivate struct TopContentView: View {
    
    let wallet: WalletItemModel
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
            OutlinedCheckmarkRadioButton(selected: wallet.isSelected, onClick: {})
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

