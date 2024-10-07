import SwiftUI

struct BottomContentView: View {
    var body: some View {
        HStack(spacing: 16) {
            BalanceItemView(isIncome: true)
            Spacer()
            BalanceItemView(isIncome: false)
        }
    }
}

fileprivate struct BalanceItemView: View {
    
    let isIncome: Bool
    
    private var imageName: String {
        isIncome ? "arrow.up" : "arrow.down"
    }
    
    private var text: LocalizedStringKey {
        isIncome ? "Total Income" : "Total Expenses"
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Spacer()
                Image(systemName: imageName)
            }
            Text(text)
                .foregroundStyle(ColorTheme.outline)
                .font(Typography.bodyMedium)
            Text("Rp. 0")
                .font(Typography.titleMedium)
        }
    }
}

#Preview {
    BottomContentView()
        .padding()
}
