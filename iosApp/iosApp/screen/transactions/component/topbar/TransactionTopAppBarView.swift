import SwiftUI

struct TransactionTopAppBarView: View {
    @Binding var query: String
    let selectedType: String
    let selectedDate: Int64
    let selectedCategory: String
    let onClick: (TransactionsUiEvent) -> Void
    let onNavigateBack: () -> Void
    
    var body: some View {
        VStack {
            VStack(spacing: 16) {
                TransactionNavBarView(onClick: onNavigateBack)
                OutlinedSearchTextFieldView(query: $query, placeHolder: "Search Transactions...")
                FilterSelectorView(
                    selectedType: selectedType,
                    selectedDate: selectedDate,
                    selectedCategory: selectedCategory,
                    onClick: onClick
                )
            }
            .padding(16)
            .background(ColorTheme.surface)
            Divider()
        }
    }
}

#Preview {
    TransactionTopAppBarView(
        query: .constant(""),
        selectedType: "InCome",
        selectedDate: 0,
        selectedCategory: "",
        onClick: {_ in },
        onNavigateBack: {}
    )
}
