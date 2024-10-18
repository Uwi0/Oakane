import SwiftUI

struct TransactionTopAppBarView: View {
    let onNavigateBack: () -> Void
    var body: some View {
        VStack(spacing: 8) {
            TransactionNavBarView(onClick: onNavigateBack)
        }
        .padding(16)
    }
}

#Preview {
    TransactionTopAppBarView(onNavigateBack: {})
}
