import SwiftUI

struct TransactionNavBarView: View {

    var showDrawer: Bool = false
    let onClick: () -> Void
    
    var body: some View {
        HStack(spacing: 24) {
            IconButtonView(
                name: showDrawer ? "line.3.horizontal" :"arrow.left",
                width: 24,
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
