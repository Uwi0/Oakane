import SwiftUI
import Shared

struct FilterByCategoryView: View {
    
    let uiState: TransactionsState
    let onEvent: (TransactionsEvent) -> Void
    
    var body: some View {
        VStack {
            CategoriesView(
                categories: uiState.categories,
                onClick: { category in
                    onEvent(.FilterByCategory(value: category))
                }
            )
            Spacer()
            FilledButtonView(
                text: "Apply Filter",
                onClick: { onEvent(.HideSheet())}
            )
            .frame(height: 48)
        }
        .padding(16)
    }
}

#Preview {
    FilterByCategoryView(
        uiState: TransactionsState(),
        onEvent: { _ in }
    )
}
