import SwiftUI
import Shared

struct TransactionTypeIconView: View {
    
    private let transactionType: TransactionType
    private let iconSize: CGFloat
    
    init(transactionType: TransactionType, iconSize: CGFloat = 48) {
        self.transactionType = transactionType
        self.iconSize = iconSize
    }
    
    private var transactionStyle: (image: String, backgroundColor: Color, tint: Color) {
        switch transactionType {
        case .income:
            return ("arrow.up", ColorTheme.primary, ColorTheme.onPrimary)
        case .expense:
            return ("arrow.down", ColorTheme.error, ColorTheme.onError)
        }
    }
    
    var body: some View {
        Circle()
            .fill(transactionStyle.backgroundColor)
            .frame(width: iconSize, height: iconSize)
            .overlay(
                Image(systemName: transactionStyle.image)
                    .resizable()
                    .foregroundColor(transactionStyle.tint)
                    .aspectRatio(contentMode: .fit)
                    .padding(iconSize * 0.25)
                    .fontWeight(.bold)
            )
    }
}

#Preview {
    TransactionTypeIconView(transactionType: .expense, iconSize: 24)
}
