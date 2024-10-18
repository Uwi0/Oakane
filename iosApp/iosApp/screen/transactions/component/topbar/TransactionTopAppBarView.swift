import SwiftUI

struct TransactionTopAppBarView: View {
    @Binding var query: String
    let onNavigateBack: () -> Void
    var body: some View {
        VStack {
            VStack(spacing: 16) {
                TransactionNavBarView(onClick: onNavigateBack)
                OutlinedSearchTextFieldView(query: $query, placeHolder: "Search Transactions...")
            }
            .padding(16)
            .background(ColorTheme.surface)
            HorizontalDivider()
        }
    }
}

#Preview {
    TransactionTopAppBarView(query: .constant(""),onNavigateBack: {})
}
