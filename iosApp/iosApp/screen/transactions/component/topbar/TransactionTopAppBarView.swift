import SwiftUI
import Shared

struct TransactionTopAppBarView: View {
    
    let uiState: TransactionsState
    let onEvent: (TransactionsEvent) -> Void
    @State private  var query: String
    
    init(uiState: TransactionsState, onEvent: @escaping (TransactionsEvent) -> Void) {
        self.uiState = uiState
        self.onEvent = onEvent
        self.query = uiState.searchQuery
    }
    
    var body: some View {
        VStack {
            VStack(spacing: 16) {
                TransactionNavBarView(onClick: { onEvent(.NavigateBack())})
                
                OutlinedSearchTextFieldView(query: $query, placeHolder: "Search Transactions...")
                    .onChange(of: query) {
                        onEvent(.FilterBy(query: query))
                    }
                
                FilterSelectorView(
                    uiState: uiState,
                    onEvent: onEvent
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
        uiState: TransactionsState.companion.default(),
        onEvent: {_ in }
    )
}
