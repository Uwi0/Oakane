import SwiftUI

struct TransactionNavBarView: View {
    let onClick: () -> Void
    var body: some View {
        HStack(spacing: 24) {
            IconButtonView(
                name: "arrow.left",
                size: 16,
                onClick: onClick
            )
            Text("Transactions")
                .font(Typography.titleMedium)
            Spacer()
        }
    }
}

#Preview {
    TransactionNavBarView(onClick: {})
}
